package de.merkeg.vsrentbe.media;
import de.merkeg.vsrentbe.exception.MediaDeletionNoPermissionException;
import de.merkeg.vsrentbe.exception.MediaNotFoundException;
import de.merkeg.vsrentbe.exception.QuotaExceededException;
import de.merkeg.vsrentbe.media.dto.MediaMetadataDTO;
import de.merkeg.vsrentbe.quota.UserQuotaService;
import de.merkeg.vsrentbe.user.User;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class MediaService {

    @Value("${s3.bucket}")
    private String s3Bucket;

    private final S3Client s3Client;
    private final UserQuotaService userQuotaService;

    @SneakyThrows
    public String uploadMedia(MultipartFile file, String altText, User owner) {
        String uuid = UUID.randomUUID().toString();

        if((file.getSize() + owner.getQuota().getUsedQuota()) > owner.getQuota().getMaxQuota()) {
            throw new QuotaExceededException();
        }

        Map<String, String> ownMetadata = new HashMap<>();
        ownMetadata.put("owner", owner.getId());
        ownMetadata.put("alt", altText);

        log.debug("Starting media upload for file '{}' with object id '{}' from user {}", file.getOriginalFilename(), uuid, owner.getId());

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Bucket)
                .key(uuid)
                .contentType(file.getContentType())
                .metadata(ownMetadata)
                .tagging(Tagging.builder().tagSet(Tag.builder().key("owner").value(owner.getId()).build()).build())
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available()));
        log.debug("Finished media upload for file '{}' with object id '{}' from user {}", file.getOriginalFilename(), uuid, owner.getId());
        userQuotaService.addToQuota(owner, file.getSize());


        return uuid;
    }

    public ResponseInputStream<GetObjectResponse> downloadMedia(String uuid) {

        try {
            return s3Client.getObject(GetObjectRequest.builder().bucket(s3Bucket).key(uuid).build());
        } catch (NoSuchKeyException e) {
            throw new MediaNotFoundException();
        }
    }

    public MediaMetadataDTO getMediaMetadata(String uuid) {
        try {
            HeadObjectResponse response = s3Client.headObject(HeadObjectRequest.builder().bucket(s3Bucket).key(uuid).build());
            Map<String, String> metadata = response.metadata();


            return MediaMetadataDTO.builder()
                    .contentType(response.contentType())
                    .uuid(uuid)
                    .altText(metadata.get("alt"))
                    .owner(metadata.get("owner"))
                    .size(response.contentLength())
                    .build();

        } catch (NoSuchKeyException e) {
            throw new MediaNotFoundException();
        }
    }

    public void deleteMedia(String uuid, User user, boolean force) {
        MediaMetadataDTO mediaMetadata = getMediaMetadata(uuid);

        if(!Objects.equals(user.getId(), mediaMetadata.getOwner()) && !force) {
            throw new MediaDeletionNoPermissionException();
        }

        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(s3Bucket).key(uuid).build());
        userQuotaService.addToQuota(user, -mediaMetadata.getSize());

    }

}

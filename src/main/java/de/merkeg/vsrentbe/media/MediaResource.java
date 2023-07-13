package de.merkeg.vsrentbe.media;


import de.merkeg.vsrentbe.media.dto.MediaMetadataDTO;
import de.merkeg.vsrentbe.user.Permission;
import de.merkeg.vsrentbe.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/media")
@Tag(name = "media")
@RequiredArgsConstructor
@Log4j2
public class MediaResource {

    private final MediaService mediaService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('media:upload')")
    @Operation(summary = "Upload new media onto the page")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("altText") String altText, Authentication authentication) throws URISyntaxException {
        User user = (User) authentication.getPrincipal();
        String uuid = mediaService.uploadMedia(file, altText, user);
        return ResponseEntity.created(new URI("/v1/media/"+uuid)).body(uuid);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @Operation(summary = "Get the media")
    public ResponseEntity<Resource> getMedia(@PathVariable String id) {
        ResponseInputStream<GetObjectResponse> stream = mediaService.downloadMedia(id);
        InputStreamResource resource = new InputStreamResource(stream);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(stream.available())
                .contentType(MediaType.parseMediaType(stream.response().contentType()))
                .body(resource);
    }
    @SneakyThrows
    @GetMapping("/{id}/metadata")
    @Operation(summary = "Get media metadata")
    public MediaMetadataDTO getMediaMeta(@PathVariable String id) {
        return mediaService.getMediaMetadata(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete media")
    @PreAuthorize("hasAuthority('media:delete')")
    public void deleteMedia(@PathVariable String id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        // Check if user has permission to delete any media
        boolean forceDelete = true;
        GrantedAuthority grantedAuthority1 = authentication.getAuthorities().stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals(Permission.ADMIN_MEDIA_DELETE.getPermission()))
                .findAny()
                .orElse(null);
        if(grantedAuthority1 == null) {
            forceDelete = false;
        }

        mediaService.deleteMedia(id, user, forceDelete);
    }
}

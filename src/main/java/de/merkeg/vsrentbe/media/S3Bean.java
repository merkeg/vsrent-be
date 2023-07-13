package de.merkeg.vsrentbe.media;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.util.AwsHostNameUtils;
import software.amazon.awssdk.endpoints.Endpoint;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointParams;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointProvider;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Log4j2
@Component
public class S3Bean {

    @Value("${s3.bucket}")
    private String s3Bucket;

    @Value("${s3.host}")
    private String s3Host;

    @Value("${s3.access_key}")
    private String s3AccessKey;

    @Value("${s3.secret_key}")
    private String s3SecretKey;

    @SneakyThrows
    @Bean
    public S3Client createS3Client() {

        AwsCredentials credentials = AwsBasicCredentials.create(s3AccessKey, s3SecretKey);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);



        S3Client client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .endpointOverride(new URI(s3Host))
                .region(Region.EU_WEST_1)
                .forcePathStyle(true)
                .build();


        // Create Bucket if it does not exist
        try {
            client.headBucket(HeadBucketRequest.builder().bucket(s3Bucket).build());
        } catch (NoSuchBucketException ex ) {
            log.info("S3 Bucket {} was not found, creating Bucket", s3Bucket);
            client.createBucket(CreateBucketRequest.builder().bucket(s3Bucket).build());
        }

        return client;
    }
}

package de.merkeg.vsrentbe.media.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaMetadataDTO {
    String uuid;
    String owner;
    String contentType;
    long size;
    String altText;
}

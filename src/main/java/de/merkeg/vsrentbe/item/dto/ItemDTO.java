package de.merkeg.vsrentbe.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {

    String id;
    String name;
    String description;
    Integer quantity;
    String imageHandle;
    boolean enabled;
    String organisation;
    String creator;

}

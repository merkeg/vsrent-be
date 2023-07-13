package de.merkeg.vsrentbe.org.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrgCreationRequestDTO {

    @NotBlank
    @Size(min = 2, message = "Must have at least the length 2")
    String name;

    @NotBlank
    String description;

    String imageHandle;
}

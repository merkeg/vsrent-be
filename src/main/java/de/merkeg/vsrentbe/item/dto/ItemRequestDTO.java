package de.merkeg.vsrentbe.item.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRequestDTO {
    @NotBlank
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters long")
    @Pattern(regexp = "^[\\w\\-\\s]+$")
    private String name;

    @NotBlank
    @Size(min = 3, max = 255, message = "description must be between 3 and 255 characters long")
    private String description;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer quantity;

    @NotBlank
    private String imageHandle;
}

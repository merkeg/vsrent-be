package de.merkeg.vsrentbe.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    @NotBlank
    public String refreshToken;
}

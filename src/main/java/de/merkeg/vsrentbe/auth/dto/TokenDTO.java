package de.merkeg.vsrentbe.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {

    public String refreshToken;
    public String accessToken;
}

package de.merkeg.vsrentbe.auth.dto;

import de.merkeg.vsrentbe.user.UserRegistrationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class UserLoginGuestDTO {
    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 8, message = "Password must have at least 8 characters")
    String password;
}

package de.merkeg.vsrentbe.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterGuestDTO {

    @NotBlank(message = "email is required")
    @Email
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    String password;

    @NotBlank(message = "Full name is required")
    String fullName;

    // TODO: PHONE NUMBER VALIDATION
    String phoneNumber;

}
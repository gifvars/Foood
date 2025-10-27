package com.foood.commons_svc.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}

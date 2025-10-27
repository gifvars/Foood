package com.foood.commons_svc.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserRequest(
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @Pattern(regexp = "CUSTOMER|RESTUARANT|DRIVER", message = "Invalid category")
        String userType,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password)
{
}

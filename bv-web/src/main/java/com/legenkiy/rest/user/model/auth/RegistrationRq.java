package com.legenkiy.rest.user.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegistrationRq(
        @NotNull
        @NotBlank
        @Length(min = 1, max = 50)
        String name,
        @NotNull
        @NotBlank
        @Length(min = 1, max = 100)
        String surname,
        @NotNull
        @NotBlank
        @Length(min = 1)
        @Email
        String email,
        @NotNull
        @NotBlank
        @Length(min = 1, max = 30)
        String username,
        @NotNull
        @NotBlank
        @Length(min = 8)
        String password
) {
}

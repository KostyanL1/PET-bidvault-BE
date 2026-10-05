package com.legenkiy.user.model.auth;

public record RegistrationRq(
        String name,
        String surname,
        String email,
        String username,
        String password
) {
}

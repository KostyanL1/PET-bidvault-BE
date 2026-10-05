package com.legenkiy.user.dto;

public record UserRegistrationCommand(
        String name,
        String surname,
        String email,
        String username,
        String password
) {
}

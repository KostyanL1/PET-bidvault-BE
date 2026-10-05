package com.legenkiy.user.dto;

public record UserLoginCommand(
        String username,
        String password
) {
}

package com.legenkiy.user.exception.model;

public record ErrorDto(
        int code,
        String message
) {
}

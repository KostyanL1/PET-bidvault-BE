package com.legenkiy.rest.user.exception.model;

public record ErrorDto(
        int code,
        String message
) {
}

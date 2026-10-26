package com.legenkiy.rest.exception.model;

public record ErrorDto(
        int code,
        String message
) {
}

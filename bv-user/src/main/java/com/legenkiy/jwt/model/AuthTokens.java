package com.legenkiy.jwt.model;

public record AuthTokens(
    String accessToken,
    String refreshToken
) {
}

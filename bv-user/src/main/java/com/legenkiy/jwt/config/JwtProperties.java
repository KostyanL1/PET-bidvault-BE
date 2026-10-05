package com.legenkiy.jwt.config;

import io.smallrye.config.ConfigMapping;

import java.time.Duration;

@ConfigMapping(prefix = "jwt")
public interface JwtProperties {
    Token refresh();
    Token access();

    interface Token {
        Duration expired();
    }
}

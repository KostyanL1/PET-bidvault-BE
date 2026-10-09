package com.legenkiy;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class CommonGenerator {

    public UUID uuid() {
        return UUID.randomUUID();
    }

    public Instant now() {
        return Instant.now();
    }

}

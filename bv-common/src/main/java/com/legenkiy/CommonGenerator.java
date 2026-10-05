package com.legenkiy;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class CommonGenerator {

    public UUID uuid() {
        return UUID.randomUUID();
    }

    public Instant now() {
        return Instant.now();
    }

}

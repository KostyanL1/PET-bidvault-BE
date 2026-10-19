package com.legenkiy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private UUID id;
    private UUID userId;
    private double balance;
    private Instant updatedAt;
    private int version;
}

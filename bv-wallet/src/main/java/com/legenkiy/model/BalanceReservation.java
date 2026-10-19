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
public class BalanceReservation {
    private UUID id;
    private UUID walletId;
    private UUID auctionId;
    private double amount;
    private Instant createdAt;
    private Instant updatedAt;
}

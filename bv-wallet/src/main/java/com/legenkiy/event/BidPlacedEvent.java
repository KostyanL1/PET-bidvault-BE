package com.legenkiy.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record BidPlacedEvent(
        UUID bidId,
        UUID roomId,
        UUID userId,
        double amount,
        Instant createdAt
) {
}

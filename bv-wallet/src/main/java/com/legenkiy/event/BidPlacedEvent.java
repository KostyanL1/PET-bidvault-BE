package com.legenkiy.event;

import java.time.Instant;
import java.util.UUID;

public record BidPlacedEvent(
        UUID bidId,
        UUID roomId,
        UUID userId,
        double amount,
        Instant createdAt
) {
}

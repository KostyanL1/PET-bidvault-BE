package com.legenkiy.dto;

import java.util.UUID;

public record CreateAuctionCommand(
        String title,
        String description,
        double startPrice,
        long duration,
        UUID ownerId
) {
}

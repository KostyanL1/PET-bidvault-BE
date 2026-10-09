package com.legenkiy.dto;

import java.util.UUID;

public record UpdateAuctionCommand(
        UUID id,
        String title,
        String description,
        Double finishPrice,
        Long duration
) {
}

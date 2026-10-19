package com.legenkiy.rest.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MakeBidRq(
        @NotNull(message = "Room id must be present")
        UUID roomId,
        @Min(value = 1, message = "Bid must be at least 1$")
        double amount
) {
}

package com.legenkiy.dto;

import java.util.UUID;

public record MakeBidCommand(
        UUID roomId,
        UUID userId,
        double amount
) {
}

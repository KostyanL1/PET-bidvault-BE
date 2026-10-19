package com.legenkiy.dto;

import java.util.UUID;

public record TopUpCommand(
        UUID userId,
        double amount
) {
}

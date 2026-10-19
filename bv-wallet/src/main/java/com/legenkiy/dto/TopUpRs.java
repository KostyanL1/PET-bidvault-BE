package com.legenkiy.dto;

import java.util.UUID;

public record TopUpRs(
        UUID userId,
        double amount
) {
}

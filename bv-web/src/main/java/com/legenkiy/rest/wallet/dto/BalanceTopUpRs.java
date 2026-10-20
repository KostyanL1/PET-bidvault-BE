package com.legenkiy.rest.wallet.dto;

import java.util.UUID;

public record BalanceTopUpRs(
        UUID userId,
        double amount
) {
}

package com.legenkiy.rest.wallet.dto;

import java.util.UUID;

public record WalletRs(
        UUID id,
        UUID userId,
        double balance
) {
}

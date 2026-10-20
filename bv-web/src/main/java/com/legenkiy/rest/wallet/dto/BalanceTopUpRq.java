package com.legenkiy.rest.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BalanceTopUpRq(
        @NotNull(message = "User id must be present")
        UUID userId,
        @Min(value = 1, message = "Amount can't be less then 1$")
        double amount
) {
}

package com.legenkiy.dto;

import java.util.UUID;

public record CreateBalanceReservationRq(
        UUID roomId,
        UUID walletId,
        double amount
) {
}

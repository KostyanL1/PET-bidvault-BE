package com.legenkiy;

import com.legenkiy.dto.CreateBalanceReservationRq;
import com.legenkiy.model.BalanceReservation;

import java.util.UUID;

public interface ReservationService {

    BalanceReservation create(CreateBalanceReservationRq rq);

    double getAmountByWalletId(UUID walletId);

}

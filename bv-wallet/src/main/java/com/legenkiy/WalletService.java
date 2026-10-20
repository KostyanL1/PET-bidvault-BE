package com.legenkiy;

import com.legenkiy.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Wallet create(UUID userId);

    Optional<Wallet> findByUserId(UUID userId);

    Wallet getByUserId(UUID userId);

    Wallet increaseBalance(UUID userId, double amount);

}

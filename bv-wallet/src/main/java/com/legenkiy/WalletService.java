package com.legenkiy;

import com.legenkiy.model.Wallet;

import java.util.UUID;

public interface WalletService {

    Wallet create(UUID userId);

}

package com.legenkiy.impl;

import com.legenkiy.MockedTopUpService;
import com.legenkiy.WalletService;
import com.legenkiy.dto.TopUpCommand;
import com.legenkiy.dto.TopUpRs;
import com.legenkiy.model.Wallet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class MockedTopUpServiceImpl implements MockedTopUpService {

    private final WalletService walletService;

    @Override
    @ActivateRequestContext
    public TopUpRs topUpBalance(TopUpCommand command) {
        log.info("Balance topped up for userId = {}, amount = {}", command.userId(), command.amount());
        Wallet wallet = walletService.increaseBalance(command.userId(), command.amount());
        return new TopUpRs(wallet.getUserId(), wallet.getBalance());
    }
}

package com.legenkiy.impl;

import com.legenkiy.WalletService;
import com.legenkiy.mapper.WalletEntityMapper;
import com.legenkiy.model.Wallet;
import com.legenkiy.repository.DefaultWalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletEntityMapper mapper;
    private final DefaultWalletRepository repository;

    @Override
    public Wallet create(UUID userId) {
        Wallet wallet = mapper.toCreate(userId);
        repository.persist(mapper.toEntity(wallet));
        return wallet;
    }
}

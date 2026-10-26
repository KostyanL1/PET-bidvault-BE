package com.legenkiy.impl;

import com.legenkiy.WalletService;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.mapper.WalletEntityMapper;
import com.legenkiy.model.Wallet;
import com.legenkiy.model.WalletEntity;
import com.legenkiy.repository.DefaultWalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletEntityMapper mapper;
    private final DefaultWalletRepository repository;

    @Override
    @Transactional
    public Wallet create(UUID userId) {
        Wallet wallet = mapper.toCreate(userId);
        repository.persist(mapper.toEntity(wallet));
        return wallet;
    }

    @Override
    public Optional<Wallet> findByUserId(UUID userId) {
        return repository.findByUserId(userId).map(mapper::toDto);
    }

    @Override
    public Wallet getByUserId(UUID userId) {
        return findByUserId(userId).orElseThrow(
                () -> new NotFoundException("Wallet with userId = %s not found".formatted(userId)));
    }

    @Override
    @Transactional
    public Wallet increaseBalance(UUID userId, double amount) {
        WalletEntity wallet = repository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException("Wallet with userId = %s not found".formatted(userId)));
        return mapper.increaseBalance(wallet, amount);
    }
}

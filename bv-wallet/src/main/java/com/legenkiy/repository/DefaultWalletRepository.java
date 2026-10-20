package com.legenkiy.repository;

import com.legenkiy.model.WalletEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DefaultWalletRepository implements PanacheRepositoryBase<WalletEntity, UUID> {
    public Optional<WalletEntity> findByUserId(UUID userId) {
        return find("userId", userId).firstResultOptional();
    }
}

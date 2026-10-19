package com.legenkiy.repository;

import com.legenkiy.model.WalletEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultWalletRepository implements PanacheRepositoryBase<WalletEntity, UUID> {
}

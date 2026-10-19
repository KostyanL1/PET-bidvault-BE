package com.legenkiy.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.model.Wallet;
import com.legenkiy.model.WalletEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class WalletEntityMapper {

    private final CommonGenerator generator;

    public Wallet toCreate(UUID userId) {
        return Wallet.builder()
                .id(generator.uuid())
                .userId(userId)
                .balance(0)
                .version(1)
                .updatedAt(generator.now())
                .build();
    }

    public Wallet toDto(WalletEntity entity) {
        return Wallet.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .balance(entity.getBalance())
                .updatedAt(entity.getUpdatedAt())
                .version(entity.getVersion())
                .build();
    }

    public WalletEntity toEntity(Wallet dto) {
        return WalletEntity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .balance(dto.getBalance())
                .updatedAt(dto.getUpdatedAt())
                .version(dto.getVersion())
                .build();
    }

}

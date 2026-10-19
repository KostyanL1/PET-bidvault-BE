package com.legenkiy.mapper;

import com.legenkiy.model.BalanceReservation;
import com.legenkiy.model.BalanceReservationEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BalanceReservationEntityMapper {

    public BalanceReservationEntity toEntity(BalanceReservation dto) {
        return BalanceReservationEntity.builder()
                .id(dto.getId())
                .walletId(dto.getWalletId())
                .auctionId(dto.getAuctionId())
                .amount(dto.getAmount())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public BalanceReservation toDto(BalanceReservationEntity entity) {
        return BalanceReservation.builder()
                .id(entity.getId())
                .walletId(entity.getWalletId())
                .auctionId(entity.getAuctionId())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

package com.legenkiy.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.dto.CreateBalanceReservationRq;
import com.legenkiy.model.BalanceReservation;
import com.legenkiy.model.BalanceReservationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class BalanceReservationEntityMapper {

    private final CommonGenerator generator;

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

    public BalanceReservation toCreate(CreateBalanceReservationRq rq, UUID auctionId) {
        Instant now = generator.now();
        return BalanceReservation.builder()
                .id(generator.uuid())
                .walletId(rq.walletId())
                .auctionId(auctionId)
                .amount(rq.amount())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

}

package com.legenkiy.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.model.Bid;
import com.legenkiy.model.BidEntity;
import com.legenkiy.model.BidStatus;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@ApplicationScoped
@RequiredArgsConstructor
public class BidEntityMapper {

    private final CommonGenerator generator;

    public BidEntity toEntity(Bid dto) {
        return BidEntity.builder()
                .id(dto.getId())
                .roomId(dto.getRoomId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .status(dto.getStatus())
                .build();
    }

    public Bid toDto(BidEntity entity) {
        return Bid.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .build();
    }

    public Bid toCreate(MakeBidCommand command) {
        Instant now = generator.now();
        return Bid.builder()
                .id(generator.uuid())
                .roomId(command.roomId())
                .userId(command.userId())
                .amount(command.amount())
                .createdAt(now)
                .updatedAt(now)
                .status(BidStatus.CREATED)
                .build();
    }

}

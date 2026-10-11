package com.legenkiy;

import com.legenkiy.dto.CreateAuctionCommand;
import com.legenkiy.dto.UpdateAuctionCommand;
import com.legenkiy.model.Auction;
import com.legenkiy.model.AuctionEntity;
import com.legenkiy.model.AuctionStatus;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@ApplicationScoped
@RequiredArgsConstructor
public class AuctionEntityMapper {

    private final CommonGenerator commonGenerator;

    public Auction toDto(AuctionEntity entity) {
        return Auction.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .startPrice(entity.getStartPrice())
                .finishPrice(entity.getFinishPrice())
                .ownerId(entity.getOwnerId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .startedAt(entity.getStartedAt())
                .finishedAt(entity.getFinishedAt())
                .duration(entity.getDuration())
                .countOfParticipants(entity.getCountOfParticipants())
                .build();
    }

    public AuctionEntity toEntity(Auction dto) {
        return AuctionEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .startPrice(dto.getStartPrice())
                .finishPrice(dto.getFinishPrice())
                .ownerId(dto.getOwnerId())
                .createdAt(dto.getCreatedAt())
                .finishedAt(dto.getFinishedAt())
                .updatedAt(dto.getUpdatedAt())
                .startedAt(dto.getStartedAt())
                .duration(dto.getDuration())
                .countOfParticipants(dto.getCountOfParticipants())
                .build();
    }

    public Auction toCreate(CreateAuctionCommand command) {
        return Auction.builder()
                .id(commonGenerator.uuid())
                .title(command.title())
                .description(command.description())
                .status(AuctionStatus.CREATED)
                .startPrice(command.startPrice())
                .ownerId(command.ownerId())
                .createdAt(commonGenerator.now())
                .duration(command.duration())
                .countOfParticipants(0)
                .build();
    }

    public void markWithStatus(Auction auction, AuctionStatus status) {
        Instant time = commonGenerator.now();
        if (status.equals(AuctionStatus.FINISHED)) {
            auction.setFinishedAt(time);
        }
        if (status.equals(AuctionStatus.STARTED)) {
            auction.setStartedAt(time);
        } else {
            throw new IllegalArgumentException("Unsupported status: status=%s".formatted(status));
        }
        auction.setUpdatedAt(time);
    }

    public void updateAuction(Auction auction, UpdateAuctionCommand command) {
        if (command.title() != null && !command.title().isBlank()) {
            auction.setTitle(command.title());
        }
        if (command.description() != null && !command.description().isBlank()) {
            auction.setDescription(command.description());
        }
        if (command.finishPrice() != null && !command.finishPrice().isNaN()) {
            auction.setFinishPrice(command.finishPrice());
        }
        if (command.duration() != null && command.duration().describeConstable().isPresent()) {
            auction.setDuration(command.duration());
        }
    }

}

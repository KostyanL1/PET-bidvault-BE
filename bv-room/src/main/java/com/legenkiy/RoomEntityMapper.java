package com.legenkiy;

import com.legenkiy.model.Room;
import com.legenkiy.model.RoomEntity;
import com.legenkiy.model.RoomStatus;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class RoomEntityMapper {

    private final CommonGenerator commonGenerator;

    public Room toDto(RoomEntity entity) {
        return Room.builder()
                .id(entity.getId())
                .auctionId(entity.getAuctionId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .openedAt(entity.getOpenedAt())
                .closedAt(entity.getClosedAt())
                .build();
    }

    public RoomEntity toEntity(Room room) {
        return RoomEntity.builder()
                .id(room.getId())
                .auctionId(room.getAuctionId())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .openedAt(room.getOpenedAt())
                .closedAt(room.getClosedAt())
                .build();
    }

    public Room toCreate(UUID auctionId) {
        Instant now = commonGenerator.now();
        return Room.builder()
                .id(commonGenerator.uuid())
                .auctionId(auctionId)
                .status(RoomStatus.CREATED)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void markWithStatus(RoomEntity entity, RoomStatus status) {
        Instant now = commonGenerator.now();
        entity.setStatus(status);
        entity.setUpdatedAt(now);
        if (RoomStatus.OPENED.equals(status)) {
            entity.setOpenedAt(now);
        }
        if (RoomStatus.CLOSED.equals(status)) {
            entity.setClosedAt(now);
        }
    }
}

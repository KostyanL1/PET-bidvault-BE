package com.legenkiy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private UUID id;
    private UUID auctionId;
    private RoomStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant openedAt;
    private Instant closedAt;
}

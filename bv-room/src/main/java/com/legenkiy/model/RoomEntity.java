package com.legenkiy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "bv_room")
public class RoomEntity {
    @Id
    @Column
    private UUID id;
    @Column(name = "auction_id")
    private UUID auctionId;
    @Enumerated(EnumType.STRING)
    @Column
    private RoomStatus status;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "opened_at")
    private Instant openedAt;
    @Column(name = "closed_at")
    private Instant closedAt;
}

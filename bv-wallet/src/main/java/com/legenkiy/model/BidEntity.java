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
@Table(name = "bv_bids")
public class BidEntity {
    @Id
    @Column
    private UUID id;
    @Column(name = "room_id")
    private UUID roomId;
    @Column(name = "user_id")
    private UUID userId;
    @Column
    private double amount;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column
    @Enumerated(EnumType.STRING)
    private BidStatus status;
}

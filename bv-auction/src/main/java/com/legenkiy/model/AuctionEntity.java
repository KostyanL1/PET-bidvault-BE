package com.legenkiy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "bv_auction")
public class AuctionEntity {
    @Id
    @Column
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private AuctionStatus status;
    @Column(name = "start_price")
    private double startPrice;
    @Column(name = "finish_price")
    private double finishPrice;
    @Column(name = "owner_id")
    private UUID ownerId;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "finished_at")
    private Instant finishedAt;
    @Column
    private long duration;
    @Column(name = "count_of_participants")
    private int countOfParticipants;
}

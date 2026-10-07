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
public class Auction {
    private UUID id;
    private String title;
    private String description;
    private AuctionStatus status;
    private double startPrice;
    private double finishPrice;
    private UUID ownerId;
    private Instant createdAt;
    private Instant finishedAt;
    private long duration;
    private int countOfParticipants;
}

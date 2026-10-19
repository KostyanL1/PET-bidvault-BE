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
@Table(name = "bv_balance_reservations")
public class BalanceReservationEntity {
    @Id
    @Column
    private UUID id;
    @Column(name = "wallet_id")
    private UUID walletId;
    @Column(name = "auction_id")
    private UUID auctionId;
    @Column
    private double amount;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
}

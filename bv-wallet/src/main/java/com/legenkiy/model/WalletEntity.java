package com.legenkiy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@Table(name = "bv_wallet")
public class WalletEntity {
    @Id
    @Column
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Column
    private double balance;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Version
    @Column
    private int version;
}

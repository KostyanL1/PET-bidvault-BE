package com.legenkiy.jwt.model;

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
@Table(name = "bv_refresh_tokens")
public class RefreshTokenEntity {
    @Id
    @Column
    private UUID jti;
    @Column(name = "user_id")
    private UUID userId;
    @Column
    private String token;
    @Column(name = "created_at")
    private Instant createdAt;
}

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
@Table(name = "bv_revoked_refresh_tokens")
public class RevokedRefreshTokenEntity {
    @Id
    @Column
    private UUID jti;
    @Column(name = "revoked_at")
    private Instant revokedAt;
}

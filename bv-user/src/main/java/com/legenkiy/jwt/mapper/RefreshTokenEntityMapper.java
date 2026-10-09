package com.legenkiy.jwt.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.jwt.model.RefreshToken;
import com.legenkiy.jwt.model.RefreshTokenEntity;
import com.legenkiy.jwt.model.RevokedRefreshTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class RefreshTokenEntityMapper {

    private final CommonGenerator commonGenerator;

    public RefreshToken toCreateDto(String token, UUID userId, UUID jti, Instant expiredAt) {
        return RefreshToken.builder()
                .jti(jti)
                .userId(userId)
                .token(token)
                .createdAt(commonGenerator.now())
                .expiredAt(expiredAt)
                .build();
    }

    public RefreshToken toDto(RefreshTokenEntity token) {
        return RefreshToken.builder()
                .jti(token.getJti())
                .userId(token.getUserId())
                .createdAt(token.getCreatedAt())
                .token(token.getToken())
                .expiredAt(token.getExpiredAt())
                .build();
    }

    public RefreshTokenEntity toEntity(RefreshToken token) {
        return RefreshTokenEntity.builder()
                .jti(token.getJti())
                .userId(token.getUserId())
                .createdAt(token.getCreatedAt())
                .token(token.getToken())
                .expiredAt(token.getExpiredAt())
                .build();
    }

    public RevokedRefreshTokenEntity toEntity(UUID jti) {
        return RevokedRefreshTokenEntity.builder()
                .jti(jti)
                .revokedAt(commonGenerator.now())
                .build();
    }

}

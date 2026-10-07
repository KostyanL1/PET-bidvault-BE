package com.legenkiy.jwt.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.jwt.model.RefreshToken;
import com.legenkiy.jwt.model.RefreshTokenEntity;
import com.legenkiy.jwt.model.RevokedRefreshTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RefreshTokenEntityMapper {

    public RefreshToken toCreateDto(String token, UUID userId) {
        return RefreshToken.builder()
                .jti(CommonGenerator.uuid())
                .userId(userId)
                .token(token)
                .createdAt(CommonGenerator.now())
                .build();
    }

    public RefreshToken toDto(RefreshTokenEntity token) {
        return RefreshToken.builder()
                .jti(token.getJti())
                .userId(token.getUserId())
                .createdAt(token.getCreatedAt())
                .token(token.getToken())
                .build();
    }

    public RefreshTokenEntity toEntity(RefreshToken token) {
        return RefreshTokenEntity.builder()
                .jti(token.getJti())
                .userId(token.getUserId())
                .createdAt(token.getCreatedAt())
                .token(token.getToken())
                .build();
    }

    public RevokedRefreshTokenEntity toEntity(UUID jti) {
        return RevokedRefreshTokenEntity.builder()
                .jti(jti)
                .revokedAt(CommonGenerator.now())
                .build();
    }

}

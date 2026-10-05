package com.legenkiy.jwt.impl;

import com.legenkiy.CommonGenerator;
import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.config.JwtProperties;
import com.legenkiy.jwt.mapper.RefreshTokenEntityMapper;
import com.legenkiy.jwt.model.AuthTokens;
import com.legenkiy.jwt.model.RefreshToken;
import com.legenkiy.jwt.model.RefreshTokenEntity;
import com.legenkiy.jwt.model.RevokedRefreshTokenEntity;
import com.legenkiy.jwt.repository.DefaultRefreshTokenRepository;
import com.legenkiy.jwt.repository.DefaultRevokedRefreshTokenRepository;
import com.legenkiy.user.UserService;
import com.legenkiy.user.exception.NotFoundException;
import com.legenkiy.user.model.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultRefreshTokenService implements JwtService {

    private static final String TOKEN_ISSUER = "bidvault";

    private final UserService userService;
    private final JwtProperties properties;
    private final RefreshTokenEntityMapper mapper;
    private final DefaultRefreshTokenRepository refreshTokenRepository;
    private final DefaultRevokedRefreshTokenRepository revokedRefreshTokenRepository;

    @Override
    public AuthTokens issueTokens(String username, UUID userId) {
        User user = userService.getByUsername(username);

        String refreshToken = generateToken(true, user);
        String accessToken = generateToken(false, user);

        RefreshToken tokenForSave = mapper.toCreateDto(refreshToken, userId);
        RefreshToken savedToken = create(tokenForSave);

        return new AuthTokens(accessToken, savedToken.getToken());
    }

    @Override
    public boolean existRevokedTokenByJti(UUID jti) {
        return revokedRefreshTokenRepository.existsByJti(jti);
    }

    @Override
    public void revoke(String token) {
        RefreshToken refreshToken = getByToken(token);

        RefreshTokenEntity tokenEntity = mapper.toEntity(refreshToken);
        RevokedRefreshTokenEntity revokedTokenEntity = mapper.toEntity(refreshToken.getJti());

        refreshTokenRepository.delete(tokenEntity);
        revokedRefreshTokenRepository.persist(revokedTokenEntity);
    }

    private RefreshToken create(RefreshToken token) {
        RefreshTokenEntity tokenEntity = mapper.toEntity(token);
        refreshTokenRepository.persist(tokenEntity);
        return mapper.toDto(tokenEntity);
    }

    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token).map(mapper::toDto);
    }

    private RefreshToken getByToken(String token) {
        return findByToken(token).orElseThrow(() -> new NotFoundException("Token not found"));
    }

    private String generateToken(boolean isRefreshToken, User user) {
        return Jwt
                .issuer(TOKEN_ISSUER)
                .upn(String.valueOf(user.getId()))
                .groups(String.valueOf(user.getRole()))
                .issuedAt(CommonGenerator.now())
                .expiresAt(getExpirationTime(isRefreshToken))
                .sign();
    }

    private Instant getExpirationTime(boolean isRefreshToken) {
        Instant now = CommonGenerator.now();
        Duration expirationTime = isRefreshToken ? properties.refresh().expired() : properties.access().expired();
        return now.plus(expirationTime);
    }
}

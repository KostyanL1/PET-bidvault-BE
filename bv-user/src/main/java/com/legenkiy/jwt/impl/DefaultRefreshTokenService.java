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
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
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
    private final JWTParser parser;

    @Override
    @Transactional
    public AuthTokens issueTokens(String username, UUID userId) {
        User user = userService.getByUsername(username);

        UUID tokensJti = CommonGenerator.uuid();
        Instant refreshTokenExpiredAt = getExpirationTime(true);
        String refreshToken = generateToken(user, tokensJti, refreshTokenExpiredAt);
        String accessToken = generateToken(user, tokensJti, getExpirationTime(false));

        RefreshToken tokenForSave = mapper.toCreateDto(refreshToken, userId, tokensJti, refreshTokenExpiredAt);
        RefreshToken savedToken = create(tokenForSave);

        return new AuthTokens(accessToken, savedToken.getToken());
    }

    @Override
    public String validateTokenAndGetUsername(String token) {
        try {
            if (token == null || token.isBlank()) {
                log.info("Refresh token is missing");
                throw new UnauthorizedException("Refresh token is missing");
            }

            JsonWebToken jwt = parser.parse(token);

            if (!isTokenNonExpired(UUID.fromString(jwt.getTokenID()))) {
                throw new UnauthorizedException("Token expired");
            }
            if (existRevokedTokenByJti(UUID.fromString(jwt.getTokenID()))) {
                throw new UnauthorizedException("Token revoked");
            }

            return jwt.getName();
        } catch (Exception e) {
            throw new UnauthorizedException("Token validation failed: ", e.getCause());
        }
    }

    @Override
    public boolean existRevokedTokenByJti(UUID jti) {
        return revokedRefreshTokenRepository.existsByJti(jti);
    }

    @Override
    public boolean isTokenNonExpired(UUID jti) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findById(jti);
        return refreshToken.getExpiredAt().isAfter(Instant.now());
    }

    @Override
    @Transactional
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

    private String generateToken(User user, UUID jti, Instant expirationTime) {
        return Jwt
                .issuer(TOKEN_ISSUER)
                .subject(user.getId().toString())
                .upn(String.valueOf(user.getUsername()))
                .groups(String.valueOf(user.getRole()))
                .claim(Claims.jti, jti.toString())
                .issuedAt(CommonGenerator.now())
                .expiresAt(expirationTime)
                .sign();
    }

    private Instant getExpirationTime(boolean isRefreshToken) {
        Instant now = CommonGenerator.now();
        Duration expirationTime = isRefreshToken ? properties.refresh().expired() : properties.access().expired();
        return now.plus(expirationTime);
    }
}

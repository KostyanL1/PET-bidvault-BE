package com.legenkiy.jwt;

import com.legenkiy.jwt.model.AuthTokens;
import jakarta.xml.bind.ValidationException;

import java.util.UUID;

public interface JwtService {

    AuthTokens issueTokens(String username, UUID userId);

    String validateTokenAndGetUsername(String token);

    boolean existRevokedTokenByJti(UUID jti);

    boolean isTokenNonExpired(UUID jti);

    void revoke(String token);
}

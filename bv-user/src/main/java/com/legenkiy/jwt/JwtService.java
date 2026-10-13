package com.legenkiy.jwt;

import com.legenkiy.jwt.model.AuthTokens;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

public interface JwtService {

    AuthTokens issueTokens(String username, UUID userId);

    JsonWebToken validateTokenAndGetTokenObject(String token);

    boolean existRevokedTokenByJti(UUID jti);

    boolean isTokenNonExpired(UUID jti);

    void revoke(String token);
}

package com.legenkiy.jwt;

import com.legenkiy.jwt.model.AuthTokens;

import java.util.UUID;

public interface JwtService {

    AuthTokens issueTokens(String username, UUID userId);

    boolean existRevokedTokenByJti(UUID jti);

    void revoke(String token);
}

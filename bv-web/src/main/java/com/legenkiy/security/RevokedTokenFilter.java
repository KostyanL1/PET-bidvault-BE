package com.legenkiy.security;

import com.legenkiy.jwt.JwtService;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Slf4j
@Provider
@TokenFilter
@RequiredArgsConstructor
public class RevokedTokenFilter implements ContainerRequestFilter {

    private final JsonWebToken jwt;
    private final JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (jwt.getTokenID() == null) {
            return;
        }

        if (jwtService.existRevokedTokenByJti(UUID.fromString(jwt.getTokenID()))) {
            throw new NotAuthorizedException("Invalid revoked");
        }

        if (jwtService.isTokenNonExpired(UUID.fromString(jwt.getTokenID()))) {
            throw new NotAuthorizedException("Token expired");
        }
    }
}

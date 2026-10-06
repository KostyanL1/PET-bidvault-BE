package com.legenkiy.security;

import com.legenkiy.jwt.JwtService;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;
import java.util.UUID;

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
            throw new NotAuthorizedException("Token revoked");
        }
    }
}

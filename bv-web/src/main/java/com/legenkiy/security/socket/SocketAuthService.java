package com.legenkiy.security.socket;

import com.legenkiy.jwt.JwtService;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Arrays;

@ApplicationScoped
@RequiredArgsConstructor
public class SocketAuthService {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final String COOKIE_HEADER = "Cookie";

    private final JwtService jwtService;

    @ActivateRequestContext
    public JsonWebToken authenticate(WebSocketConnection connection) {
        String token = extractTokenFromCookie(connection, ACCESS_TOKEN_COOKIE);
        return jwtService.validateTokenAndGetTokenObject(token);
    }

    private String extractTokenFromCookie(WebSocketConnection connection, String tokenName) {
        String cookieHeader = connection.handshakeRequest().header(COOKIE_HEADER);

        if (cookieHeader == null || cookieHeader.isBlank()) {
            throw new UnauthorizedException("Missing token");
        }

        return Arrays.stream(cookieHeader.split(";"))
                .map(String::trim)
                .filter(cookie -> cookie.startsWith(tokenName + "="))
                .map(cookie -> cookie.substring((tokenName + "=").length()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Missing token"));
    }
}

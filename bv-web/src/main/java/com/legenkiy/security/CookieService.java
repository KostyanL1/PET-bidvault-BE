package com.legenkiy.security;

import com.legenkiy.jwt.model.AuthTokens;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CookieService {

    /*
    equals 7 days
     */
    private final static int REFRESH_TOKEN_MAX_AGE = 604800;
    private final static int ACCESS_TOKEN_MAX_AGE = 60;

    public TokenCookies produceCookies(AuthTokens tokens) {
        return new TokenCookies(
                createCookieWithToken(tokens.accessToken(), ACCESS_TOKEN_MAX_AGE, false),
                createCookieWithToken(tokens.refreshToken(), REFRESH_TOKEN_MAX_AGE, true)
        );
    }

    public TokenCookies destroyCookies() {
        return new TokenCookies(
                createCookieWithToken(null, 0, false),
                createCookieWithToken(null, 0, true)
        );
    }

    private NewCookie createCookieWithToken(String token, int maxAge, boolean isRefresh) {
        return new NewCookie.Builder(
                isRefresh ? "refresh_token" : "access_token"
        )
                .value(token)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite(NewCookie.SameSite.STRICT)
                .maxAge(maxAge)
                .build();
    }

    public record TokenCookies(
            NewCookie accessTokenCookie,
            NewCookie refreshTokenCookie
    ){

    }

}

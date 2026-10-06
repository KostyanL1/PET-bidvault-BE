package com.legenkiy.user.impl;

import com.legenkiy.jwt.model.AuthTokens;
import com.legenkiy.security.CookieService;
import com.legenkiy.security.CookieService.TokenCookies;
import com.legenkiy.security.TokenFilter;
import com.legenkiy.user.AuthResource;
import com.legenkiy.user.AuthService;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.dto.UserLoginCommand;
import com.legenkiy.user.model.User;
import com.legenkiy.user.exception.AlreadyAuthenticated;
import com.legenkiy.user.mapper.UserMapper;
import com.legenkiy.user.model.auth.LoginRq;
import com.legenkiy.user.model.auth.RegistrationRq;
import com.legenkiy.user.utils.ResourcesUtils;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.legenkiy.common.BaseApi.BASE_PATH;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@Path(BASE_PATH + "/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefaultAuthResource implements AuthResource {

    private final SecurityIdentity identity;
    private final AuthService authService;
    private final UserMapper mapper;
    private final CookieService cookieService;

    @Override
    @POST
    @Path(REGISTRATION_PATH)
    @PermitAll
    public Response register(@Valid RegistrationRq rq) {
        log.debug("User registration: username={}, email=%{}", rq.username(), rq.email());
        validate(rq.username());

        UserRegistrationCommand command = mapper.toUserRegistration(rq);
        User registered = authService.register(command);

        log.debug("User registered: id={}, username={}", registered.getId(), registered.getUsername());
        return Response.ok().build();
    }

    @Override
    @POST
    @Path(LOGIN_PATH)
    @PermitAll
    public Response login(@Valid LoginRq rq) {
        log.debug("User login: username={}", rq.username());
        validate(rq.username());

        UserLoginCommand command = mapper.toUserLogin(rq);
        AuthTokens tokens = authService.login(command);

        TokenCookies tokenCookies = cookieService.produceCookies(tokens);

        log.debug("User logged in: username={}", rq.username());
        return Response
                .ok(tokens)
                .cookie(tokenCookies.refreshTokenCookie(), tokenCookies.accessTokenCookie())
                .build();
    }

    @Override
    @POST
    @Path(LOGOUT_PATH)
    @TokenFilter
    @PermitAll
    public Response logout(@CookieParam("refresh_token") String refreshToken) {
        String username = ResourcesUtils.extractUsername(this.identity);
        log.debug("User logout: username={}", username);

        authService.logout(refreshToken);
        TokenCookies destroyedCookies = cookieService.destroyCookies();

        return Response
                .noContent()
                .cookie(destroyedCookies.refreshTokenCookie(), destroyedCookies.accessTokenCookie())
                .build();
    }

    private void validate(String username) {
        if (!identity.isAnonymous()) {
            String message = "User already authenticated: username=%s".formatted(username);
            log.debug(message);
            throw new AlreadyAuthenticated(message);
        }
    }

}

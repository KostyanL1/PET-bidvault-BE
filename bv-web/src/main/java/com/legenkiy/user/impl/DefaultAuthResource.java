package com.legenkiy.user.impl;

import com.legenkiy.jwt.model.AuthTokens;
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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultAuthResource implements AuthResource {

    private final SecurityIdentity identity;
    private final AuthService service;
    private final UserMapper mapper;

    @Override
    public Response register(RegistrationRq rq) {
        log.debug("User registration: username={}, email=%{}", rq.username(), rq.email());
        validate(rq.username());

        UserRegistrationCommand command = mapper.toUserRegistration(rq);
        User registered = service.register(command);

        log.debug("User registered: id={}, username={}", registered.getId(), registered.getUsername());
        return Response.ok().build();
    }

    @Override
    public Response login(LoginRq rq) {
        log.debug("User login: username={}", rq.username());
        validate(rq.username());

        UserLoginCommand command = mapper.toUserLogin(rq);
        AuthTokens tokens = service.login(command);

        log.debug("User logged in: username={}", rq.username());
        return Response.ok(tokens).build();
    }

    @Override
    @TokenFilter
    public Response logout(String authorization) {
        UUID id = ResourcesUtils.extractUUID(this.identity);
        log.debug("User logout: id={}", id);

        service.logout(authorization);

        return Response.noContent().build();
    }

    private void validate(String username) {
        if (!identity.isAnonymous()) {
            String message = "User already authenticated: username=%s".formatted(username);
            log.debug(message);
            throw new AlreadyAuthenticated(message);
        }
    }

}

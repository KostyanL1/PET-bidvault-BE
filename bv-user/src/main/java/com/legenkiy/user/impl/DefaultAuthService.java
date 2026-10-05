package com.legenkiy.user.impl;

import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.model.AuthTokens;
import com.legenkiy.user.AuthService;
import com.legenkiy.user.UserService;
import com.legenkiy.user.dto.UserLoginCommand;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.exception.AuthException;
import com.legenkiy.user.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public User register(UserRegistrationCommand command) {
        validate(command);
        return userService.create(command);
    }

    @Override
    public AuthTokens login(UserLoginCommand command) {
        validate(command);
        User user = userService.getByUsername(command.username());
        return jwtService.issueTokens(user.getUsername(), user.getId());
    }

    @Override
    public void logout(String refreshToken) {
        jwtService.revoke(refreshToken);
    }

    private void validate(UserRegistrationCommand command) {
        Optional<User> userByUsername = userService.findByUsername(command.username());
        if (userByUsername.isPresent()) {
            String message = "User with such username already registered: username=%s".formatted(command.username());
            log.debug(message);
            throw new AuthException(message);
        }
        Optional<User> userByEmail = userService.findByEmail(command.email());
        if (userByEmail.isPresent()) {
            String message = "User with such email already registered: username=%s".formatted(command.email());
            log.debug(message);
            throw new AuthException(message);
        }
    }

    private void validate(UserLoginCommand command) {
        Optional<User> user = userService.findByUsername(command.username());
        if (user.isEmpty()) {
            String message = "User with such username not found: username=%s".formatted(command.username());
            log.debug(message);
            throw new AuthException(message);
        }
        if (!BcryptUtil.matches(command.password(), user.get().getPassword())) {
            String message = "Password incorrect for username=%s".formatted(command.username());
            log.debug(message);
            throw new AuthException(message);
        }
    }


}

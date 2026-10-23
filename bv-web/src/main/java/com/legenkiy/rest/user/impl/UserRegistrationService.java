package com.legenkiy.rest.user.impl;

import com.legenkiy.WalletService;
import com.legenkiy.user.AuthService;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class UserRegistrationService {

    private final WalletService walletService;
    private final AuthService authService;

    public User register(UserRegistrationCommand command) {
        User registered = authService.register(command);
        walletService.create(registered.getId());
        return registered;
    }

}

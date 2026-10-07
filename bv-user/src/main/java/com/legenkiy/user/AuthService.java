package com.legenkiy.user;

import com.legenkiy.jwt.model.AuthTokens;
import com.legenkiy.user.dto.UserLoginCommand;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.model.User;

public interface AuthService {
    User register(UserRegistrationCommand command);

    AuthTokens login(UserLoginCommand command);

    AuthTokens refresh(String token);

    void logout(String refreshToken);
}

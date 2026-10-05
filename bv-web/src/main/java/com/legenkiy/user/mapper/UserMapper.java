package com.legenkiy.user.mapper;

import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.dto.UserLoginCommand;
import com.legenkiy.user.model.auth.LoginRq;
import com.legenkiy.user.model.auth.RegistrationRq;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public UserRegistrationCommand toUserRegistration(RegistrationRq rq) {
        return new UserRegistrationCommand(
                rq.name(),
                rq.surname(),
                rq.email(),
                rq.username(),
                rq.password());
    }

    public UserLoginCommand toUserLogin(LoginRq rq) {
        return new UserLoginCommand(
                rq.username(),
                rq.password());
    }

}

package com.legenkiy.user;

import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.model.User;

import java.util.Optional;

public interface UserService {

    User create(UserRegistrationCommand command);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    User getByUsername(String username);

}

package com.legenkiy.user.impl;

import com.legenkiy.user.UserService;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.user.mapper.UserEntityMapper;
import com.legenkiy.user.model.User;
import com.legenkiy.user.model.UserEntity;
import com.legenkiy.user.repository.DefaultUserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultIUserService implements UserService {

    private final DefaultUserRepository repository;
    private final UserEntityMapper mapper;

    @Override
    @Transactional
    public User create(UserRegistrationCommand command) {
        UserEntity userEntity = mapper.toCreate(command);
        repository.persist(userEntity);
        return mapper.toDto(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDto);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username).map(mapper::toDto);
    }

    @Override
    public User getByUsername(String username) {
        return findByUsername(username).orElseThrow(() ->
                new NotFoundException("User with such username not found: username=%s".formatted(username))
        );
    }

}

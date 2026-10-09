package com.legenkiy.user.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.model.User;
import com.legenkiy.user.model.UserEntity;
import com.legenkiy.user.model.UserRole;
import com.legenkiy.user.security.PasswordHasher;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@ApplicationScoped
@RequiredArgsConstructor
public class UserEntityMapper {

    private final CommonGenerator commonGenerator;
    private final PasswordHasher passwordHasher;

    public UserEntity toCreate(UserRegistrationCommand command) {
        Instant time = commonGenerator.now();
        return UserEntity.builder()
                .id(commonGenerator.uuid())
                .name(command.name())
                .surname(command.surname())
                .username(command.username())
                .email(command.email())
                .password(passwordHasher.hash(command.password()))
                .role(UserRole.USER)
                .createdAt(time)
                .updatedAt(time)
                .build();

    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toDto(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}

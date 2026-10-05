package com.legenkiy.user.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.user.dto.UserRegistrationCommand;
import com.legenkiy.user.model.User;
import com.legenkiy.user.model.UserEntity;
import com.legenkiy.user.model.UserRole;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;

@ApplicationScoped
public class UserEntityMapper {

    public UserEntity toCreate(UserRegistrationCommand command) {
        Instant time = CommonGenerator.now();
        return UserEntity.builder()
                .id(CommonGenerator.uuid())
                .name(command.name())
                .surname(command.surname())
                .username(command.username())
                .email(command.email())
                .password(BcryptUtil.bcryptHash(command.password()))
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

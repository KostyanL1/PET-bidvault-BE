package com.legenkiy.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
}

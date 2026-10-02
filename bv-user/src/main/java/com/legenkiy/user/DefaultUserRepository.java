package com.legenkiy.user;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import com.legenkiy.user.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DefaultUserRepository implements PanacheRepositoryBase<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

}

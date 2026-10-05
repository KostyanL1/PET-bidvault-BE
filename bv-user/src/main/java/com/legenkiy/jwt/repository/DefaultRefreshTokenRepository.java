package com.legenkiy.jwt.repository;

import com.legenkiy.jwt.model.RefreshTokenEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DefaultRefreshTokenRepository implements PanacheRepositoryBase<RefreshTokenEntity, UUID> {

    public Optional<RefreshTokenEntity> findByUserId(UUID id) {
        return find("user_id", id).firstResultOptional();
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return find("token", token).firstResultOptional();
    }

}

package com.legenkiy.jwt.repository;

import com.legenkiy.jwt.model.RevokedRefreshTokenEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultRevokedRefreshTokenRepository implements PanacheRepositoryBase<RevokedRefreshTokenEntity, UUID> {
    public boolean existsByJti(UUID jti) {
        return count("jti", jti) > 0;
    }
}

package com.legenkiy.user.impl;

import com.legenkiy.user.AuthResource;
import com.legenkiy.user.exception.AlreadyAuthenticated;
import com.legenkiy.user.model.auth.RegistrationRq;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class DefaultAuthResource implements AuthResource {

    @Inject
    SecurityIdentity identity;

    @Override
    public Response register(RegistrationRq rq) {
        if (!identity.isAnonymous()) {
            throw new AlreadyAuthenticated("User already authenticated");
        }
        return null;
    }
}

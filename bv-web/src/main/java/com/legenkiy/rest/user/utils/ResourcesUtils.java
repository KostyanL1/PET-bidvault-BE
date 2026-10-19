package com.legenkiy.rest.user.utils;

import io.quarkus.security.identity.SecurityIdentity;
import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@UtilityClass
public class ResourcesUtils {

    public static String extractUsername(SecurityIdentity identity) {
        return identity.getPrincipal().getName();
    }

    public static UUID extractPrincipalId(JsonWebToken token) {
        return UUID.fromString(token.getSubject());
    }

}

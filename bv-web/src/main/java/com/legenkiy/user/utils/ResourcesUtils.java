package com.legenkiy.user.utils;

import io.quarkus.security.identity.SecurityIdentity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ResourcesUtils {

    public static String extractUsername(SecurityIdentity identity) {
        return identity.getPrincipal().getName();
    }

}

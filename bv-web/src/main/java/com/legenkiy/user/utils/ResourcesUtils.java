package com.legenkiy.user.utils;

import io.quarkus.security.identity.SecurityIdentity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourcesUtils {

    public static String extractUsername(SecurityIdentity identity) {
        return identity.getPrincipal().getName();
    }

}

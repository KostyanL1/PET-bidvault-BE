package com.legenkiy.user.utils;

import io.quarkus.security.identity.SecurityIdentity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ResourcesUtils {

    public static UUID extractUUID(SecurityIdentity identity) {
        String uuid = identity.getPrincipal().getName();
        return UUID.fromString(uuid);
    }

}

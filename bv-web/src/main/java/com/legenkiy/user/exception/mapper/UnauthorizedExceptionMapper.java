package com.legenkiy.user.exception.mapper;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper extends AbstractExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        return buildResponse(Response.Status.UNAUTHORIZED, exception);
    }
}

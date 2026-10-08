package com.legenkiy.user.exception.mapper;

import com.legenkiy.user.exception.AuthException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper extends AbstractExceptionMapper<AuthException> {
    @Override
    public Response toResponse(AuthException exception) {
        return buildResponse(Response.Status.CONFLICT, exception);
    }
}

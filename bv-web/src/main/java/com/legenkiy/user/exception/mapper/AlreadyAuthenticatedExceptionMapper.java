package com.legenkiy.user.exception.mapper;

import com.legenkiy.user.exception.AlreadyAuthenticated;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AlreadyAuthenticatedExceptionMapper extends AbstractExceptionMapper<AlreadyAuthenticated> {
    @Override
    public Response toResponse(AlreadyAuthenticated exception) {
        return buildResponse(Response.Status.CONFLICT, exception);
    }
}

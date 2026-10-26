package com.legenkiy.rest.exception.mapper;

import com.legenkiy.rest.exception.AlreadyAuthenticated;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AlreadyAuthenticatedExceptionMapper extends AbstractExceptionMapper<AlreadyAuthenticated> {
    @Override
    public Response toResponse(AlreadyAuthenticated exception) {
        return buildResponse(Response.Status.CONFLICT, exception);
    }
}

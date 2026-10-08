package com.legenkiy.user.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper extends AbstractExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return buildResponse(Response.Status.BAD_REQUEST, exception);
    }
}

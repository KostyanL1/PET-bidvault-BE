package com.legenkiy.user.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BaseExceptionMapper extends AbstractExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, exception);
    }
}

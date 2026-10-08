package com.legenkiy.user.exception.mapper;

import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper extends AbstractExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        return buildResponse(Response.Status.BAD_REQUEST, exception);
    }
}

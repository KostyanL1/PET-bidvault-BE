package com.legenkiy.user.exception.mapper;

import com.legenkiy.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends AbstractExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return buildResponse(Response.Status.NOT_FOUND, exception);
    }
}

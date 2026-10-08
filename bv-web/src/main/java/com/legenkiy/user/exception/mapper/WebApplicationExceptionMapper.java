package com.legenkiy.user.exception.mapper;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper extends AbstractExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        return buildResponse(
                Response.Status.fromStatusCode(exception.getResponse().getStatus()),
                exception
        );
    }
}

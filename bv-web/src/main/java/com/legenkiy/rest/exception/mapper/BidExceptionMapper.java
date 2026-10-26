package com.legenkiy.rest.exception.mapper;

import com.legenkiy.excpetion.BidException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BidExceptionMapper extends AbstractExceptionMapper<BidException> {
    @Override
    public Response toResponse(BidException exception) {
        return buildResponse(Response.Status.BAD_REQUEST, exception);
    }
}

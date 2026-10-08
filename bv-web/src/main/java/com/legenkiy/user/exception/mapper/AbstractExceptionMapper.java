package com.legenkiy.user.exception.mapper;

import com.legenkiy.user.exception.model.ErrorDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractExceptionMapper<T extends RuntimeException> implements ExceptionMapper<T> {

    private static String LOG_FORMAT = "%s [%s]: %s";

    public Response buildResponse(Response.Status status, T exception) {
        logError(status, exception);
        return Response
                .status(status)
                .entity(new ErrorDto(status.getStatusCode(), exception.getMessage()))
                .build();
    }

    private void logError(Response.Status status, T exception) {
        String message = LOG_FORMAT.formatted(
                exception.getClass().getSimpleName(),
                status.getStatusCode(),
                exception.getMessage()
        );
        log.error(message);
    }

}

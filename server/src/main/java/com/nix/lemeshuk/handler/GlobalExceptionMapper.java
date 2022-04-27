package com.nix.lemeshuk.handler;

import com.nix.lemeshuk.model.ErrorResponseMessage;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class GlobalExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {

        Response errorResponse = exception.getResponse();

        int errorStatusCode = errorResponse.getStatus();
        Response.Status errorStatus = Response.Status.fromStatusCode(errorStatusCode);

        return Response.status(errorStatusCode)
                .entity(ErrorResponseMessage.fromStatus(errorStatus))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
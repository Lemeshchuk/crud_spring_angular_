package com.nix.lemeshuk.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nix.lemeshuk.model.ErrorResponseMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse res,
                       AccessDeniedException e) throws IOException {

        Response.Status errorStatus = Response.Status.FORBIDDEN;

        res.setContentType(MediaType.APPLICATION_JSON);
        res.setStatus(errorStatus.getStatusCode());

        OutputStream out = res.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, ErrorResponseMessage.fromStatus(errorStatus));

        out.flush();
    }
}

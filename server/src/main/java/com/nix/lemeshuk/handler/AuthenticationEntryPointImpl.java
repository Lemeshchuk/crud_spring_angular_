package com.nix.lemeshuk.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nix.lemeshuk.model.ErrorResponseMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        Response.Status errorStatus = Response.Status.UNAUTHORIZED;

        res.setContentType(MediaType.APPLICATION_JSON);
        res.setStatus(errorStatus.getStatusCode());

        OutputStream out = res.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, ErrorResponseMessage.fromStatus(errorStatus));

        out.flush();
    }
}

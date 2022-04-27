package com.nix.lemeshuk.service.authenticate;

import com.nix.lemeshuk.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

@Slf4j
@Component

public class AuthenticateServiceImpl implements AuthenticateService {

    private final AuthenticationManager authenticationManager;

    private static final String BASE_API_URL = "/api";

    public AuthenticateServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User authenticate(String username, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (User) authentication.getPrincipal();
    }

    @Override
    public Response logout(MessageContext context) {

        HttpServletResponse response = context.getHttpServletResponse();

        String jwtCookieName = "token";
        Cookie cookie = new Cookie(jwtCookieName, null);
        cookie.setPath(BASE_API_URL);
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return Response.ok().build();
    }
}

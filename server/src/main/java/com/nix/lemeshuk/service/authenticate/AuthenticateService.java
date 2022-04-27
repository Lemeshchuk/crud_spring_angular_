package com.nix.lemeshuk.service.authenticate;

import com.nix.lemeshuk.model.User;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public interface AuthenticateService {
    User authenticate(String username, String password) throws AuthenticationException;

    Response logout(@Context MessageContext context);
}

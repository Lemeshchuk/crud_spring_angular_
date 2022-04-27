package com.nix.lemeshuk.controller;

import com.nix.lemeshuk.dto.AddedUserDto;
import com.nix.lemeshuk.dto.AuthenticationRequestDto;
import com.nix.lemeshuk.service.authenticate.AuthenticateService;
import com.nix.lemeshuk.service.usersService.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Component

@CrossOriginResourceSharing(allowAllOrigins = true, allowCredentials = true, maxAge = 3600)
public class SignInAndOutController {

    private final AuthenticateService authenticateService;
    private final UsersService usersService;

    public SignInAndOutController(AuthenticateService authenticateService, UsersService usersService) {
        this.authenticateService = authenticateService;
        this.usersService = usersService;
    }


    @POST
    @Path("auth/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequestDto requestDto) {

        return usersService.login(requestDto);
    }

    @POST
    @Path("/auth/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerNewUser(AddedUserDto userDto,
                                    @QueryParam("recaptcha-response") String captchaResponse) {

        return usersService.registerNewUser(userDto, captchaResponse);
    }

    @GET
    @Path("auth/logout")
    public Response logout(@Context MessageContext context) {

        return authenticateService.logout(context);
    }
}


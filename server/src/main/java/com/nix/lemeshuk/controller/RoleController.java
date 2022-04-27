package com.nix.lemeshuk.controller;

import com.nix.lemeshuk.dao.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@RequiredArgsConstructor
@CrossOriginResourceSharing(allowAllOrigins = true, allowCredentials = true, maxAge = 3600)
public class RoleController {

    public final RoleService roleService;

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        return Response.ok().entity(roleService.findAll()).build();
    }
}

package com.nix.lemeshuk.controller;

import com.nix.lemeshuk.dao.UserService;
import com.nix.lemeshuk.dto.AddedUserDto;
import com.nix.lemeshuk.dto.EditedUserDto;
import com.nix.lemeshuk.model.ErrorResponseMessage;
import com.nix.lemeshuk.service.usersService.UsersService;
import com.nix.lemeshuk.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.nix.lemeshuk.util.Constant.UNPROCESSABLE_ENTITY_ERROR;
import static com.nix.lemeshuk.util.Constant.UNPROCESSABLE_ENTITY_ERROR_CODE;

@Slf4j
@Component
@RequiredArgsConstructor
@CrossOriginResourceSharing(allowAllOrigins = true, allowCredentials = true, maxAge = 3600)
public class AdminController {

    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final UsersService usersService;

    @GET
    @Path("admin/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        return Response.ok().entity(userService.findAll()).build();
    }

    @GET
    @Path("admin/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        return Response.ok().entity(userService.findById(id).get()).build();
    }

    @POST
    @Path("admin/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(AddedUserDto userDto) {
        return usersService.addUser(userDto);
    }

    @PUT
    @Path("admin/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(EditedUserDto userDto) {
        boolean isUserUpdated = false;

        if (validationUtil.isValid(userDto)) {
            isUserUpdated = userService.update(userService.toUser(userDto));
        }
        if (isUserUpdated) {
            return Response.ok().build();
        }
        ErrorResponseMessage errorResponseMessage = ErrorResponseMessage.of(
                UNPROCESSABLE_ENTITY_ERROR_CODE, "UNPROCESSABLE_ENTITY", UNPROCESSABLE_ENTITY_ERROR);

        return Response.status(errorResponseMessage.getStatus()).entity(errorResponseMessage).build();
    }

    @DELETE
    @Path("admin/user/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteById(id);

        return Response.ok().build();
    }
}

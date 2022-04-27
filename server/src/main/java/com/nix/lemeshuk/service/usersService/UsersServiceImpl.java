package com.nix.lemeshuk.service.usersService;

import com.nix.lemeshuk.dao.RoleService;
import com.nix.lemeshuk.dao.UserService;
import com.nix.lemeshuk.dto.AddedUserDto;
import com.nix.lemeshuk.dto.AuthenticationRequestDto;
import com.nix.lemeshuk.dto.AuthenticationResponseDto;
import com.nix.lemeshuk.dto.CaptchaResponseDto;
import com.nix.lemeshuk.dto.UserDto;
import com.nix.lemeshuk.model.ErrorResponseMessage;
import com.nix.lemeshuk.model.User;
import com.nix.lemeshuk.service.authenticate.AuthenticateService;
import com.nix.lemeshuk.util.CaptchaResponseUtil;
import com.nix.lemeshuk.util.JwtUtil;
import com.nix.lemeshuk.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static com.nix.lemeshuk.util.Constant.UNPROCESSABLE_ENTITY_ERROR;
import static com.nix.lemeshuk.util.Constant.UNPROCESSABLE_ENTITY_ERROR_CODE;

@Slf4j
@Component

public class UsersServiceImpl implements UsersService {

    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CaptchaResponseUtil captchaResponseUtil;
    private final AuthenticateService authenticateService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;

    public final String CAPTCHA_NOT_PASSED_ERROR = "Captcha not passed";
    public final int CAPTCHA_NOT_PASSED_ERROR_CODE = 429;
    public final String NO_CONTENT_ERROR = "No Content";
    public final String AUTHENTICATION_FAILED = "Authentication failed. Incorrect username (%s) or password.";
    public final int NO_CONTENT = 204;

    public UsersServiceImpl(ValidationUtil validationUtil, UserService userService, CaptchaResponseUtil captchaResponseUtil, AuthenticateService authenticateService, RoleService roleService, JwtUtil jwtUtil) {
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.captchaResponseUtil = captchaResponseUtil;
        this.authenticateService = authenticateService;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }

    public Response addUser(UserDto userDto) {
        boolean isSuccessful = false;

        if (validationUtil.isValid(userDto)) {
            isSuccessful = userService.create(userService.toUser(userDto));
        }

        if (isSuccessful) {
            return Response.ok().build();
        }

        ErrorResponseMessage errorResponseMessage = ErrorResponseMessage.of(
                UNPROCESSABLE_ENTITY_ERROR_CODE, "UNPROCESSABLE_ENTITY", UNPROCESSABLE_ENTITY_ERROR);

        return Response.status(errorResponseMessage.getStatus()).entity(errorResponseMessage).build();
    }

    @Override
    public Response registerNewUser(AddedUserDto userDto, String captchaResponse) {

        CaptchaResponseDto captchaResponseDto = captchaResponseUtil.getCaptchaResponse(captchaResponse);

        if (!captchaResponseDto.isSuccess()) {
            ErrorResponseMessage errorResponseMessage = ErrorResponseMessage.of(
                    CAPTCHA_NOT_PASSED_ERROR_CODE, "CAPTCHA_NOT_PASSED", CAPTCHA_NOT_PASSED_ERROR);

            return Response.status(errorResponseMessage.getStatus()).entity(errorResponseMessage).build();
        }

        return addUser(userDto);
    }

    public Response login(AuthenticationRequestDto requestDto) {

        if (validationUtil.isValid(requestDto)) {
            try {
                User authenticatedUser = authenticateService.authenticate(requestDto.getUsername(), requestDto.getPassword());
                ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(authenticatedUser);

                String currentUserRoleName = authenticatedUser.getRole().getName();
                String defaultRolePage = roleService.getDefaultRolePage(currentUserRoleName);

                AuthenticationResponseDto responseDto = AuthenticationResponseDto.builder()
                        .username(authenticatedUser.getUsername())
                        .firstName(authenticatedUser.getFirstName())
                        .roleName(currentUserRoleName)
                        .defaultRolePage(defaultRolePage)
                        .build();

                return Response.ok()
                        .header("token", jwtUtil.generateTokenFromUsername(authenticatedUser.getUsername()))
                        .entity(responseDto)
                        .build();

            } catch (AuthenticationException e) {
                log.info(String.format(AUTHENTICATION_FAILED, requestDto.getUsername()));

                ErrorResponseMessage errorResponseMessage = ErrorResponseMessage.of(
                        NO_CONTENT, "NO_CONTENT_ERROR", NO_CONTENT_ERROR);

                return Response.status(errorResponseMessage.getStatus()).entity(errorResponseMessage).build();
            }
        }

        ErrorResponseMessage errorResponseMessage = ErrorResponseMessage.of(
                UNPROCESSABLE_ENTITY_ERROR_CODE, "UNPROCESSABLE_ENTITY", UNPROCESSABLE_ENTITY_ERROR);

        return Response.status(errorResponseMessage.getStatus()).entity(errorResponseMessage).build();
    }
}

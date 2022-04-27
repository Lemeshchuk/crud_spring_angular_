package com.nix.lemeshuk.service.usersService;

import com.nix.lemeshuk.dto.AddedUserDto;
import com.nix.lemeshuk.dto.AuthenticationRequestDto;
import com.nix.lemeshuk.dto.UserDto;

import javax.ws.rs.core.Response;

public interface UsersService {
    Response addUser(UserDto userDto);

    Response login(AuthenticationRequestDto requestDto);

    Response registerNewUser(AddedUserDto userDto, String captchaResponse);
}

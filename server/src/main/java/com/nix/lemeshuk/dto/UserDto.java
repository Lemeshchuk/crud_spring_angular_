package com.nix.lemeshuk.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;
    String roleName;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    LocalDate birthday;
}

package com.nix.lemeshuk.dto;

import com.nix.lemeshuk.util.JwtUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDto {

    @NotBlank
    @Size(min = 4, max = 25)
    String username;

    @NotBlank
    @Size(min = 4, max = 25)
    String firstName;


    String roleName;
    String defaultRolePage;
}

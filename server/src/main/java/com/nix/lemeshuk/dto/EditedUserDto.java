package com.nix.lemeshuk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditedUserDto extends UserDto {

    Long id;

    @ToString.Exclude
    String roleName;

    @NotBlank
    @Size(min = 4, max = 25)
    String username;

    @ToString.Exclude
    @Size(max = 100)
    String password;

    @Email
    @Size(min = 14, max = 38)
    String email;

    @NotBlank
    @Size(min = 3, max = 25)
    String firstName;

    @NotBlank
    @Size(min = 3, max = 25)
    String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    LocalDate birthday;
}

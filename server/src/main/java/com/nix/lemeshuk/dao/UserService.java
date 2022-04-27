package com.nix.lemeshuk.dao;

import com.nix.lemeshuk.model.User;
import com.nix.lemeshuk.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean create(User user);

    boolean update(User user);

    void delete(User user);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User toUser(UserDto userDto);

}

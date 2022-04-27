package com.nix.lemeshuk.dao;

import com.nix.lemeshuk.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    boolean create(Role role);

    boolean update(Role role);

    void remove(Role role);

    List<Role> findAll();

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);

    String getDefaultRolePage(String userRoleName);
}

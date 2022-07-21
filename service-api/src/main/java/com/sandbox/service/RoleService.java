package com.sandbox.service;

import com.sandbox.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAllRoles();

    Optional<Role> findRoleByName(String name);
}

package com.sandbox.service;

import com.sandbox.dto.RoleDto;

public interface RoleService {

    RoleDto findRoleByName(String name);

    RoleDto addNewRole(String name);
}

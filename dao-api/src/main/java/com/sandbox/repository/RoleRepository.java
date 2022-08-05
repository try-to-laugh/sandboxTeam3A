package com.sandbox.repository;

import com.sandbox.dto.RoleDto;

import java.util.Optional;

public interface RoleRepository {

    Optional<RoleDto> findByName(String name);

    RoleDto save(RoleDto roleDto);
}

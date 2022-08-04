package com.sandbox.repository.impl;

import com.sandbox.dto.RoleDto;
import com.sandbox.entity.Role;
import com.sandbox.mapper.RoleMapper;
import com.sandbox.repository.RoleRepository;
import com.sandbox.repository.RoleRepositoryJpa;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleRepositoryJpa roleRepositoryJpa;
    private final RoleMapper roleMapper;

    @Override
    public Optional<RoleDto> findByName(String name) {
        Optional<Role> role = roleRepositoryJpa.findByName(name);
        return role.map(roleMapper::toRoleDto);
    }
}

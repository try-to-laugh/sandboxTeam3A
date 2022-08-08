package com.sandbox.repository.impl;

import com.sandbox.dto.RoleDto;
import com.sandbox.entity.Role;
import com.sandbox.mapper.RoleMapper;
import com.sandbox.repository.RoleRepository;
import com.sandbox.repository.RoleRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleRepositoryJpa roleRepositoryJpa;
    private final RoleMapper roleMapper;

    @Override
    public Optional<RoleDto> findByName(String name) {
        Optional<Role> role = roleRepositoryJpa.findByName(name);
        return role.map(roleMapper::toRoleDto);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = roleMapper.toRole(roleDto);
        return roleMapper.toRoleDto(roleRepositoryJpa.save(role));
    }
}

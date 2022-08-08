package com.sandbox.service;

import com.sandbox.dto.RoleDto;

import com.sandbox.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto findRoleByName(String name) {
        return roleRepository.findByName(name).orElseGet(() -> addNewRole("USER"));
    }

    @Override
    public RoleDto addNewRole(String name) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        return roleRepository.save(roleDto);
    }
}

package com.sandbox.service;

import com.sandbox.dto.RoleDto;
import com.sandbox.mapper.RoleMapper;
import com.sandbox.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto findRoleByName(String name) {
        return roleMapper.toRoleDto(roleRepository.findByName(name)
                .orElseGet(() -> roleMapper.toRole(addNewRole("USER"))));
    }

    @Override
    public RoleDto addNewRole(String name) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        return roleMapper.toRoleDto(roleRepository.save(roleMapper.toRole(roleDto)));
    }


}

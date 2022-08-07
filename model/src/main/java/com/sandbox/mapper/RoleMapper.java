package com.sandbox.mapper;

import com.sandbox.dto.RoleDto;
import com.sandbox.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);

    Role toRole(RoleDto roleDto);

    Set<RoleDto> toSetRolesDto(Set<Role> role);
}

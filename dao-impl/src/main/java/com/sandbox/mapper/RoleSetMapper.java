package com.sandbox.mapper;

import com.sandbox.dto.RoleDto;
import com.sandbox.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface RoleSetMapper {

    Set<RoleDto> toDtoRoleSet(Set<Role> roles);
}

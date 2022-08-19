package com.sandbox.service;

import com.sandbox.dto.RoleDto;
import com.sandbox.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserDto> findAllUsers();

    UserDto findUserByUsername(String username);

    Long saveUser(UserDto userDto);

    UserDto addRoleToUser(UserDto userDto, RoleDto roleDto);

    UserDto encodeAndSetPasswordToUser(UserDto userDto, PasswordEncoder passwordEncoder);

    Set<RoleDto> getUserRoles(UserDto userDto);
}

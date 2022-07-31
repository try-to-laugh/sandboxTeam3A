package com.sandbox.service;

import com.sandbox.dto.RoleDto;
import com.sandbox.dto.UserDto;
import com.sandbox.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    List<UserDto> findAllUsers();

    Optional<User> findUserById(Long id);

    UserDto findUserByEmail(String email);

    void saveUser(UserDto userDto);

    UserDto addRoleToUser(UserDto userDto, RoleDto roleDto);

    UserDto encodeAndSetPasswordToUser(UserDto userDto, PasswordEncoder passwordEncoder);

    Set<RoleDto> getUserRoles(UserDto userDto);
}

package com.sandbox.service;

import com.sandbox.dto.RoleDto;
import com.sandbox.dto.UserDto;
import com.sandbox.exception.UsernameAlreadyExistsException;
import com.sandbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Long saveUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        return userRepository.save(userDto);
    }

    @Override
    public UserDto addRoleToUser(UserDto userDto, RoleDto roleDto) {
        userDto.getRoles().add(roleDto);
        return userDto;
    }

    @Override
    public UserDto encodeAndSetPasswordToUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDto;
    }

    @Override
    public Set<RoleDto> getUserRoles(UserDto userDto) {
        return userDto.getRoles();
    }
}

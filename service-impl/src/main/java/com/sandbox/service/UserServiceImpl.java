package com.sandbox.service;

import com.sandbox.dto.RoleDto;
import com.sandbox.dto.UserDto;
import com.sandbox.entity.User;
import com.sandbox.mapper.RoleMapper;
import com.sandbox.mapper.UserMapper;
import com.sandbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public List<UserDto> findAllUsers() {
        return userMapper.toUsersDtoList(userRepository.findAll());
    }

    @Override
    public UserDto findUserByUsername(String username) {
        return userMapper.toUserDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public void saveUser(UserDto userDto) {
        userRepository.save(userMapper.toUser(userDto));
    }

    @Override
    public UserDto addRoleToUser(UserDto userDto, RoleDto roleDto) {
        User user = userMapper.toUser(userDto);
        user.getRoles().add(roleMapper.toRole(roleDto));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto encodeAndSetPasswordToUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserDto(user);
    }

    @Override
    public Set<RoleDto> getUserRoles(UserDto userDto) {
        return roleMapper.toSetRolesDto(userMapper.toUser(userDto).getRoles());
    }
}

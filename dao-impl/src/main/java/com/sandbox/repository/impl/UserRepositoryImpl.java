package com.sandbox.repository.impl;

import com.sandbox.dto.UserDto;
import com.sandbox.entity.User;
import com.sandbox.mapper.UserMapper;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(userMapper::toUserDto);
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toUsersDtoList(userRepository.findAll());
    }

    @Override
    public void save(UserDto userDto) {
        userRepository.save(userMapper.toUser(userDto));
    }
}

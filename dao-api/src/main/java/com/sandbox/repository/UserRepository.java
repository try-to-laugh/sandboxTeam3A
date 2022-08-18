package com.sandbox.repository;

import com.sandbox.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserDto> findByUserId(Long userId);

    Optional<UserDto> findByUsername(String username);

    List<UserDto> findAll();

    Long save(UserDto userDto);

    Optional<UserDto> findById(Long idUser);
}

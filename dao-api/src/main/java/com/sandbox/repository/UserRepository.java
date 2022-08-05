package com.sandbox.repository;

import com.sandbox.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<UserDto> findByUsername(String username);

    List<UserDto> findAll();

    void save(UserDto userDto);
}

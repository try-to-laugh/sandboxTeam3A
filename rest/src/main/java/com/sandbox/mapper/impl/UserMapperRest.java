package com.sandbox.mapper.impl;

import com.sandbox.dto.UserDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.UserLoginDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapperRest implements MapperRest<UserDto, UserLoginDto> {

    @Override
    public UserDto toDto(UserLoginDto apiDto) {
        return UserDto.builder()
                .username(apiDto.getUsername())
                .password(apiDto.getPassword())
                .roles(new HashSet<>())
                .build();
    }

    @Override
    public UserLoginDto toApiDto(UserDto dto) {
        return null;
    }
}

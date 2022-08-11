package com.sandbox.mapper;

import com.sandbox.dto.UserDto;
import com.sandbox.model.UserLoginDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperRest {

    UserDto fromUserLoginDtoToUserDto(UserLoginDto userLoginDto);
}

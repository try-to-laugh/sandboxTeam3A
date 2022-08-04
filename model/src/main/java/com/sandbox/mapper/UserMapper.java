package com.sandbox.mapper;

import com.sandbox.dto.UserDto;
import com.sandbox.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleSetMapper.class})
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    List<UserDto> toUsersDtoList(List<User> users);
}

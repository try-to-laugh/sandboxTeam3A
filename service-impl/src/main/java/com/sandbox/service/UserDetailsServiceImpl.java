package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.mapper.UserMapper;
import com.sandbox.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = userService.findUserByUsername(username);
        return new UserDetailsImpl(userMapper.toUser(userDto));
    }
}

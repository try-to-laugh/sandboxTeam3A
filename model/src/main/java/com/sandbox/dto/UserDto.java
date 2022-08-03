package com.sandbox.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Set<RoleDto> roles = new HashSet<>();
}

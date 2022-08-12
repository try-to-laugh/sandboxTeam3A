package com.sandbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Set<RoleDto> roles = new HashSet<>();
    private Set<WalletDto> wallets = new HashSet<>();
}

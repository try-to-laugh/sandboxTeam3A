package com.sandbox.repository;

import com.sandbox.dto.RoleDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<RoleDto> findByName(String name);
}

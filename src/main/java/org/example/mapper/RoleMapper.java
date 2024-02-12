package org.example.mapper;

import org.example.dto.RoleDto;
import org.example.model.Role;

public class RoleMapper {

    public RoleDto toDto(Role role) {
        return new RoleDto(role.getName());
    }

    public Role toEntity(RoleDto roleDto) {
        return new Role(roleDto.getName());
    }
}

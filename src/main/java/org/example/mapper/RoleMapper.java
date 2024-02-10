package org.example.mapper;

import org.example.dto.RoleCreationDto;
import org.example.dto.RoleDto;
import org.example.model.Role;

public class RoleMapper {

    public RoleDto roleToDto(Role role) {
        return new RoleDto(role.getName());
    }

    public Role dtoToUser(RoleCreationDto roleDto) {
        return new Role(roleDto.getName());
    }
}

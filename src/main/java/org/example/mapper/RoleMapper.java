package org.example.mapper;

import org.example.dto.RoleCreationDto;
import org.example.dto.RoleDto;
import org.example.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role toEntity(RoleCreationDto roleDto);

    RoleDto toDto(Role role);

    List<RoleDto> toDto(List<Role> roles);


}

package by.kladvirov.mapper;

import by.kladvirov.dto.RoleCreationDto;
import by.kladvirov.dto.RoleDto;
import by.kladvirov.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role toEntity(RoleCreationDto roleDto);

    RoleDto toDto(Role role);

    List<RoleDto> toDto(List<Role> roles);


}

package by.kladvirov.mapper;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.model.Role;
import by.kladvirov.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", source = "userCreationDto.roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserCreationDto userCreationDto);

    UserDto toDto(User user);

    @Mapping(target = "roleIds", source = "user.roles", qualifiedByName = "mapRolesToRoleIds")
    UserCreationDto toCreationDto(User user);

    List<UserDto> toDto(List<User> users);

    @AfterMapping
    default void setBlockedToFalse(@MappingTarget User.UserBuilder user) {
        user.isBlocked(false);
    }

    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapRolesToRoleIds")
    default List<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

}

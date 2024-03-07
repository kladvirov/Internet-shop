package by.kladvirov.mapper;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreationDto userCreationDto);

    UserDto toDto(User user);

    // need?
    UserCreationDto toCreationDto(User user);

    List<UserDto> toDto(List<User> users);

    @AfterMapping
    default void setBlockedToFalse(@MappingTarget User.UserBuilder user) {
        user.isBlocked(false);
    }

}

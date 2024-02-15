package org.example.mapper;

import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toEntity(UserCreationDto userDto);

    UserDto toDto(User user);
}

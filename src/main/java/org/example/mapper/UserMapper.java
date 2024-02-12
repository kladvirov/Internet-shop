package org.example.mapper;

import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.model.User;

public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getSurname(), user.getBirthDate(), user.getLogin());
    }

    public User toEntity(UserCreationDto userDto) {
        return new User(userDto.getName(), userDto.getSurname(), userDto.getBirthDate(), userDto.getLogin(),
                userDto.getSurname(), userDto.getIsBlocked());
    }
}

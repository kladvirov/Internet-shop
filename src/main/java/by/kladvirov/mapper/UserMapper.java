package by.kladvirov.mapper;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User toEntity(UserCreationDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);


}

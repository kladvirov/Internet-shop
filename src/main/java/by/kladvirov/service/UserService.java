package by.kladvirov.service;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.model.User;
import by.kladvirov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("There is no entity with the following id"));
        return userMapper.toDto(user);
    }

    public List<UserDto> findAll(int size, int page) {
        return userMapper.toDto(userRepository.findAll(size, page));
    }

    public UserDto save(UserCreationDto userDto) {
        User entity = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(entity));
    }

    public void update(Long id, UserCreationDto userDto) {
        userRepository.update(id, userMapper.toEntity(userDto));
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

}

package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id));
    }

    public List<UserDto> findAll(int size, int page) {
        return userRepository.findAll(size, page).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto save(UserCreationDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    public void update(UserCreationDto userDto) {
        userRepository.update(userMapper.toEntity(userDto));
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}

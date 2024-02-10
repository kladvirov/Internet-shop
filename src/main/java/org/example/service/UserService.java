package org.example.service;

import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findUser(Long id) {
        return new UserMapper().userToDto(userRepository.findById(id));
    }

    public List<UserDto> findAllUsers(int size, int page) {
        UserMapper userMapper = new UserMapper();
        return userRepository.findAll(size, page).stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

}

package by.kladvirov.service;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.model.User;
import by.kladvirov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }

    public List<UserDto> findAll(int size, int page) {
        try {
            return userMapper.toDto(userRepository.findAll(size, page));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during finding all users", HttpStatus.BAD_REQUEST);
        }
    }

    public UserDto save(UserCreationDto userDto) {
        try {
            User entity = userMapper.toEntity(userDto);
            return userMapper.toDto(userRepository.save(entity));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during saving user", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, UserCreationDto userDto) {
        try {
            userRepository.update(id, userMapper.toEntity(userDto));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.delete(id);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

}

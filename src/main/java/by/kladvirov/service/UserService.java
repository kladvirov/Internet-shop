package by.kladvirov.service;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.dto.UserDto;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.model.User;
import by.kladvirov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public UserCreationDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ServiceException("There is no such user with following login", HttpStatus.NOT_FOUND));
        return userMapper.toCreationDto(user);
    }

    public List<UserDto> findAll(Pageable pageable) {
        try {
            return userMapper.toDto(userRepository.findAll(pageable).toList());
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
            User user = userRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such user", HttpStatus.NOT_FOUND));
            User mappedUser = userMapper.toEntity(userDto);
            updateUser(user, mappedUser);
            userRepository.save(user);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateUser(User user, User mappedUser) {
        user.setName(mappedUser.getName());
        user.setSurname(mappedUser.getSurname());
        user.setBirthDate(mappedUser.getBirthDate());
        user.setLogin(mappedUser.getLogin());
        user.setPassword(mappedUser.getPassword());
        user.setRoles(mappedUser.getRoles());
    }

}

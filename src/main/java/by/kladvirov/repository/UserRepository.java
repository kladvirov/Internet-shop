package by.kladvirov.repository;

import by.kladvirov.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll(int size, int page);

    User save(User user);

    void update(Long id, User user);

    void delete(Long id);

}

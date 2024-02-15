package org.example.repository;

import org.example.model.User;

import java.util.List;

public interface UserRepository {
    User findById(Long id);
    List<User> findAll(int size, int page);
    User save(User user);
    void update(User user);
    void delete(Long id);
}

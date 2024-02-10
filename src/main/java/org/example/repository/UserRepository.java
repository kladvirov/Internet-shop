package org.example.repository;

import org.example.model.User;

import java.util.List;

public interface UserRepository {
    public User findById(Long id);
    public List<User> findAll(int size, int page);
    public User save(User user);
    public void update(User user);
    public void delete(Long id);
}

package org.example.repository.hibernate;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    public User findById(Long id);
    public List<User> findAll();
    public void update(User user);
    public void save(User user);
    public void delete(Long id);
}

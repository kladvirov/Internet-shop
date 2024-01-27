package org.example.service;

import org.example.model.User;
import org.example.repository.hibernate.UserDaoImpl;

import java.util.List;

public class UserService {
    private UserDaoImpl usersDao = new UserDaoImpl();

    public UserService() {
    }
    public User findUser(Long id) {
        return usersDao.findById(id);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public void deleteUser(Long id) {
        usersDao.delete(id);
    }

}

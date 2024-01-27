package org.example;

import org.example.model.Order;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.RoleService;
import org.example.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class Main {
    public static void main(String[] args) {


        UserService userService = new UserService();
        Order order = new Order(new Timestamp(3432423), "ordered");
        Role role1 = new Role("alo");
        Role role2 = new Role("java");
        Set<Role> roles = new HashSet<>();
        RoleService roleService = new RoleService();
        User user1 = new User("Testin", "HibernateRoles228", LocalDate.of(2004, 8, 6), "123", "56789", false);
        User user2 = new User("Hiber", "Testin", LocalDate.of(2004, 8, 6), "123", "56789", false);

        Set<User> users = new HashSet<>();

        users.add(user1);
        users.add(user2);

        role1.setUsers(users);
        roleService.saveRole(role1);
    }
}
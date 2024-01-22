package org.example;

import org.example.model.Good;
import org.example.model.Order;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.GoodRepository;
import org.example.repository.OrderRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.util.ConnectionProvider;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        User user = new User(null, "Andron", "SIgMONS", LocalDate.of(2004, 8, 6), "123", "56789", true);
        Role role = new Role(null, "sasamba");
        Order order = new Order(null, 4L, new Timestamp(43274747), "ordered");
        Good good = new Good(1L, "something", "any", new BigDecimal(25), LocalDate.of(2004, 8, 6), LocalDate.of(2010, 8, 6), true);

        ConnectionProvider connectionProvider = new ConnectionProvider();
        UserRepository userRepository = new UserRepository(connectionProvider);
        RoleRepository roleRepository = new RoleRepository(connectionProvider);
        OrderRepository orderRepository = new OrderRepository(connectionProvider);
        GoodRepository goodRepository = new GoodRepository(connectionProvider);

        userRepository.insert(user);
    }
}
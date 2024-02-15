package org.example.repository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {
    Order findById(Long id);
    List<Order> findAll(int size, int page);
    Order save(Order order);
    void update(Order order);
    void delete(Long id);
}

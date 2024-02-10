package org.example.repository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {
    public Order findById(Long id);
    public List<Order> findAll(int size, int page);
    public Order save(Order order);
    public void update(Order order);
    public void delete(Long id);
}

package org.example.repository.hibernate;

import org.example.model.Order;

import java.util.List;

public interface OrderDao {
    public Order findById(Long id);
    public List<Order> findAll();
    public void save(Order order);
    public void update(Order order);
    public void delete(Long id);
}

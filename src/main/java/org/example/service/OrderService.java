package org.example.service;

import org.example.model.Order;
import org.example.repository.hibernate.OrderRepositoryImpl;

import java.util.List;

public class OrderService {
    private final OrderRepositoryImpl orderRepository;

    public OrderService(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.update(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }
}
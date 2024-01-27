package org.example.service;

import org.example.model.Order;
import org.example.repository.hibernate.OrderDaoImpl;

import java.util.List;

public class OrderService {
    private OrderDaoImpl ordersDao = new OrderDaoImpl();

    public OrderService() {
    }
    public Order findOrder(Long id) {
        return ordersDao.findById(id);
    }

    public List<Order> findAllOrders() {
        return ordersDao.findAll();
    }

    public void saveOrder(Order order) {
        ordersDao.save(order);
    }

    public void updateOrder(Order order) {
        ordersDao.update(order);
    }

    public void deleteOrder(Long id) {
        ordersDao.delete(id);
    }
}
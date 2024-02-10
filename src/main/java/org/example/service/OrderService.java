package org.example.service;

import org.example.dto.OrderDto;
import org.example.mapper.OrderMapper;
import org.example.model.Order;
import org.example.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto findOrder(Long id) {
        return new OrderMapper().orderToDto(orderRepository.findById(id));
    }

    public List<OrderDto> findAllOrders(int size, int page) {
        OrderMapper orderMapper = new OrderMapper();
        return orderRepository.findAll(size, page).stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.update(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }
}
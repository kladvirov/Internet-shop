package org.example.mapper;

import org.example.dto.OrderCreationDto;
import org.example.dto.OrderDto;
import org.example.model.Order;

public class OrderMapper {

    public OrderDto orderToDto(Order order) {
        return new OrderDto(order.getOrderDate(), order.getStatus());
    }

    public Order dtoToOrder(OrderCreationDto orderDto) {
        return new Order(orderDto.getOrderDate(), orderDto.getStatus());
    }
}

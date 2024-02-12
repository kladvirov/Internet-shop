package org.example.mapper;

import org.example.dto.OrderCreationDto;
import org.example.dto.OrderDto;
import org.example.model.Order;

public class OrderMapper {

    public OrderDto toDto(Order order) {
        return new OrderDto(order.getStatus());
    }

    public Order toEntity(OrderCreationDto orderDto) {
        return new Order(orderDto.getOrderDate(), orderDto.getStatus());
    }
}

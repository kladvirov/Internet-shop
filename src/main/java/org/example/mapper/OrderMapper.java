package org.example.mapper;

import org.example.dto.OrderCreationDto;
import org.example.dto.OrderDto;
import org.example.model.Order;

import java.time.LocalDateTime;

public class OrderMapper {

    public OrderDto toDto(Order order) {
        return new OrderDto(order.getUser(), order.getOrderDate(), order.getStatus());
    }

    public Order toEntity(OrderCreationDto orderDto) {
        return new Order(LocalDateTime.now(), orderDto.getStatus(), orderDto.getUser());
    }
}

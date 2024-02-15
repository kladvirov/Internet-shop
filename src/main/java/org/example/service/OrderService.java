package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderCreationDto;
import org.example.dto.OrderDto;
import org.example.mapper.OrderMapper;
import org.example.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id));
    }

    public List<OrderDto> findAll(int size, int page) {
        return orderRepository.findAll(size, page).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto save(OrderCreationDto orderDto) {
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    public void update(OrderCreationDto orderDto) {
        orderRepository.update(orderMapper.toEntity(orderDto));
    }

    public void delete(Long id) {
        orderRepository.delete(id);
    }
}
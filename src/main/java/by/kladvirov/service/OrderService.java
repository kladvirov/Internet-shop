package by.kladvirov.service;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.mapper.OrderMapper;
import by.kladvirov.model.Order;
import by.kladvirov.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id));
    }

    public List<OrderDto> findAll(int size, int page) {
        return orderMapper.toDto(orderRepository.findAll(size, page));
    }

    public OrderDto save(OrderCreationDto orderDto) {
        Order entity = orderMapper.toEntity(orderDto);
        return orderMapper.toDto(orderRepository.save(entity));
    }

    public void update(OrderCreationDto orderDto) {
        orderRepository.update(orderMapper.toEntity(orderDto));
    }

    public void delete(Long id) {
        orderRepository.delete(id);
    }

}
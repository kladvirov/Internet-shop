package by.kladvirov.service;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.OrderMapper;
import by.kladvirov.model.Order;
import by.kladvirov.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException("There is no entity with the following id"));
        return orderMapper.toDto(order);
    }

    public List<OrderDto> findAll(int size, int page) {
        return orderMapper.toDto(orderRepository.findAll(size, page));
    }

    public OrderDto save(OrderCreationDto orderDto) {
        Order entity = orderMapper.toEntity(orderDto);
        return orderMapper.toDto(orderRepository.save(entity));
    }

    public void update(Long id, OrderCreationDto orderDto) {
        orderRepository.update(id, orderMapper.toEntity(orderDto));
    }

    public void delete(Long id) {
        orderRepository.delete(id);
    }

}
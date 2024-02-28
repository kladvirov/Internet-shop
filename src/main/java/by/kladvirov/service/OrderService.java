package by.kladvirov.service;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.OrderMapper;
import by.kladvirov.model.Order;
import by.kladvirov.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such order", HttpStatus.NOT_FOUND));
        return orderMapper.toDto(order);
    }

    public List<OrderDto> findAll(int size, int page) {
        try {
            return orderMapper.toDto(orderRepository.findAll(size, page));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during finding all orders", HttpStatus.BAD_REQUEST);
        }
    }

    public OrderDto save(OrderCreationDto orderDto) {
        try {
            Order entity = orderMapper.toEntity(orderDto);
            return orderMapper.toDto(orderRepository.save(entity));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during saving order", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, OrderCreationDto orderDto) {
        try {
            orderRepository.update(id, orderMapper.toEntity(orderDto));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating order", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            orderRepository.delete(id);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during finding all orders", HttpStatus.BAD_REQUEST);
        }
    }

}
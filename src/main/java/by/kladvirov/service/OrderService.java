package by.kladvirov.service;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.OrderMapper;
import by.kladvirov.model.Order;
import by.kladvirov.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such order", HttpStatus.NOT_FOUND));
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAll(Pageable pageable) {
        try {
            return orderMapper.toDto(orderRepository.findAll(pageable).toList());
        } catch (Exception ex) {
            throw new ServiceException("Error during finding all orders", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public OrderDto save(OrderCreationDto orderDto) {
        try {
            Order entity = orderMapper.toEntity(orderDto);
            return orderMapper.toDto(orderRepository.save(entity));
        } catch (Exception ex) {
            throw new ServiceException("Error during saving order", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, OrderCreationDto orderDto) {
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such order", HttpStatus.NOT_FOUND));
            Order mappedOrder = orderMapper.toEntity(orderDto);
            updateOrder(order, mappedOrder);
            orderRepository.save(order);
        } catch (Exception ex) {
            throw new ServiceException("Error during updating order", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ServiceException("Error during finding all orders", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateOrder(Order order, Order mappedOrder) {
        order.setUser(mappedOrder.getUser());
        order.setGoods(mappedOrder.getGoods());
    }

}
package by.kladvirov.repository;

import by.kladvirov.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> findAll(int size, int page);

    Order save(Order order);

    void update(Long id, Order order);

    void delete(Long id);

}

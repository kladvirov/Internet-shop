package org.example.repository.jdbc;

import org.example.exception.ConnectionException;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final ConnectionProvider connectionProvider;

    private static final String FIND_ALL_QUERY = "SELECT * FROM orders";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM orders WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM orders WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO orders" +
            "  (user_id, order_date, status) VALUES " +
            " (?, ?, ?);";
    private static final String UPDATE_BY_ID_QUERY = "UPDATE users SET user_id = ?, order_date = ?, status = ? WHERE id = ?";

    public OrderRepositoryImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Order findById(Long id) {

        Order order = new Order();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                fillOrder(order, rs);
            }

        } catch (SQLException e) {
            throw new ConnectionException("Order with the following ID " + id + " hasn't been found.");
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                Order order = new Order();
                fillOrder(order, rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }

        return orders;
    }

    @Override
    public Order save(Order order) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setString(3, order.getStatus());
            int value = preparedStatement.executeUpdate();

            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        order.setId(resultSet.getLong(1));
                    }
                }
            }
            return order;
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setLong(4, order.getId());
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setString(3, order.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private void fillOrder(Order order, ResultSet rs) throws SQLException {
        order.setId(rs.getLong("id"));
        order.getUser().setId(rs.getLong("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setStatus(rs.getString("status"));
    }
}

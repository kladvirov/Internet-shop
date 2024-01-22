package org.example.repository;

import org.example.exception.ConnectionException;
import org.example.model.Good;
import org.example.model.Order;
import org.example.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodRepository {
    private ConnectionProvider connectionProvider;

    public GoodRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    private static final String FIND_ALL_QUERY = "SELECT * FROM goods";
    private static final String FIND_BY_ID_QUERY = "SELECT FROM goods WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM goods WHERE id = ?";
    private static final String DELETE_ORDER_LINKS_QUERY = "DELETE FROM orders_goods_link WHERE good_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO goods (name, surname, price, create_date, expiration_date, is_available)"
            + "VALUES( ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BY_ID_QUERY = "UPDATE goods SET name = ?, surname = ?, price = ?, create_date = ?, expiration_date = ?,"
            + " is_available = ? WHERE id = ?";

    public Good findById(Long id) {

        Good good = new Good();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                fillGood(good, rs);
            }

        } catch (SQLException e) {
            throw new ConnectionException("Good with the following ID " + id + " hasn't been found.");
        }
        return good;
    }

    public List<Good> findAll() {

        List<Good> goods = new ArrayList<Good>();

        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                Good good = new Good();
                fillGood(good, rs);
                goods.add(good);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }

        return goods;
    }


    public Good insert(Good good) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setLong(1, good.getId());
            preparedStatement.setString(1, good.getName());
            preparedStatement.setString(2, good.getSurname());
            preparedStatement.setBigDecimal(3, good.getPrice());
            preparedStatement.setDate(4, Date.valueOf(good.getCreateDate()));
            preparedStatement.setDate(5, Date.valueOf(good.getExpirationDate()));
            preparedStatement.setBoolean(6, good.isAvailable());

            int value = preparedStatement.executeUpdate();

            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        good.setId(resultSet.getLong(1));
                    }
                }
            }
            return good;
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    void updateById(Long id, Good good) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setLong(7, id);
            preparedStatement.setString(1, good.getName());
            preparedStatement.setString(2, good.getSurname());
            preparedStatement.setBigDecimal(3, good.getPrice());
            preparedStatement.setDate(4, Date.valueOf(good.getCreateDate()));
            preparedStatement.setDate(5, Date.valueOf(good.getExpirationDate()));
            preparedStatement.setBoolean(6, good.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    public void deleteById(Long id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement deleteGoodsStatement = connection.prepareStatement(DELETE_BY_ID_QUERY);
             PreparedStatement deleteLinksStatement = connection.prepareStatement(DELETE_ORDER_LINKS_QUERY)) {
            try {
                connection.setAutoCommit(false);
                deleteLinksStatement.setLong(1, id);
                deleteLinksStatement.executeUpdate();
                deleteGoodsStatement.setLong(1, id);
                deleteGoodsStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new ConnectionException(e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private void fillGood(Good good, ResultSet rs) throws SQLException {
        good.setId(rs.getLong("id"));
        good.setName(rs.getString("name"));
        good.setSurname(rs.getString("surname"));
        good.setPrice(rs.getBigDecimal("price"));
        good.setCreateDate(rs.getDate("create_date").toLocalDate());
        good.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
        good.setAvailable(rs.getBoolean("is_available"));
    }
}

package org.example.repository;

import org.example.exception.ConnectionException;
import org.example.model.User;
import org.example.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private final ConnectionProvider connectionProvider;

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String DELETE_ROLE_LINKS_QUERY = "DELETE FROM users_roles_link WHERE user_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users" +
            "  (name, surname, birth_date, login, password, is_blocked) VALUES " +
            " (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_BY_ID_QUERY = "UPDATE users SET name = ?, surname = ?, birth_date = ?, " +
            "login = ?, password = ?, is_blocked = ?  WHERE id = ?";

    public UserRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public User findById(Long id) {

        User user = new User();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                fillUser(user, rs);
            }

        } catch (SQLException e) {
            throw new ConnectionException("User with the following ID " + id + " hasn't been found.");
        }
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                User user = new User();
                fillUser(user, rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }

        return users;
    }

    public User insert(User user) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.isBlocked());

            int value = preparedStatement.executeUpdate();

            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                    }
                }
            }
            return user;
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }


    public void updateById(Long id, User user) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setLong(7, id);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.isBlocked());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) throws SQLException {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement deleteLinksStatement = connection.prepareStatement(DELETE_ROLE_LINKS_QUERY);
             PreparedStatement deleteUsersStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            try {
                connection.setAutoCommit(false);
                deleteLinksStatement.setLong(1, id);
                deleteLinksStatement.executeUpdate();
                deleteUsersStatement.setLong(1, id);
                deleteUsersStatement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback();
                throw new ConnectionException(e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private void fillUser(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setBirthDate(rs.getDate("birth_date").toLocalDate());
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setBlocked(rs.getBoolean("is_blocked"));
    }
}


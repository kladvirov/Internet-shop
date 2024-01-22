package org.example.repository;

import org.example.exception.ConnectionException;
import org.example.model.Role;
import org.example.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    private final ConnectionProvider connectionProvider;
    private static final String FIND_ALL_QUERY = "SELECT * FROM roles";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM roles WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM roles WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO roles" +
            "  (name) VALUES " + " (?);";
    private static final String UPDATE_BY_ID_QUERY = "UPDATE roles SET name = ? WHERE id = ?";

    public RoleRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Role findById(Long id) {

        Role role = new Role();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                fillRole(role, rs);
            }

        } catch (SQLException e) {
            throw new ConnectionException("Role with the following ID " + id + " hasn't been found.");
        }
        return role;
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                Role role = new Role();
                fillRole(role, rs);
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }

        return roles;
    }

    public Role insert(Role role) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setLong(1, role.getId());
            preparedStatement.setString(1, role.getName());
            int value = preparedStatement.executeUpdate();

            if (value == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        role.setId(resultSet.getLong(1));
                    }
                }
            }
            return role;
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    public void updateById(Long id, Role role) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setLong(2, id);
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private void fillRole(Role role, ResultSet rs) throws SQLException {
        role.setId(rs.getLong("id"));
        role.setName(rs.getString("name"));
    }
}

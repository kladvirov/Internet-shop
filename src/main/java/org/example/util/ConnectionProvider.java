package org.example.util;

import org.example.exception.ConnectionException;
import org.example.exception.DriverException;

import java.sql.*;

public class ConnectionProvider {
    public static final String url = "jdbc:postgresql://localhost:5432/postgres";
    public static final String userName = "postgres";
    public static final String password = "postgres";
    public static final String driverJDBC = "org.postgresql.Driver";

    public ConnectionProvider() {
        try {
            Class.forName(driverJDBC);
        } catch (ClassNotFoundException e) {
            throw new DriverException("Failed to register driver.");
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new ConnectionException("Failed to create connection.");
        }
    }
}

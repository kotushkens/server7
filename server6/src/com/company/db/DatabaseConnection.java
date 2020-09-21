package com.company.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DatabaseConnection implements AutoCloseable {

    protected Connection connection;
    private static final String URL = "jdbc:postgresql://127.0.0.1:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    protected static final String[] GENERATED_COLUMNS = {"id"};

    private final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Error while connecting to DB", e);
            System.exit(1);
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    protected int getInsertId(PreparedStatement statement) throws SQLException {
        try (ResultSet keys = statement.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getInt(keys.findColumn("id"));
            } else {
                throw new SQLException("Creating failed, no id obtained!");
            }
        }
    }
}

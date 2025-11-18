package com.solvd.hospital.persistence.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionPool {
    private static final String JDBC_URL;
    private static final String JDBC_USERNAME;
    private static final String JDBC_PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Unable to load database properties: " + e.getMessage());
        }

        JDBC_URL = properties.getProperty("jdbc.url", "jdbc:mysql://localhost:3306/mydb");
        JDBC_USERNAME = properties.getProperty("jdbc.username", "root");
        JDBC_PASSWORD = properties.getProperty("jdbc.password", "");
        try {
            Class.forName(properties.getProperty("jdbc.driver", "com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("JDBC Driver not found: " + e.getMessage());
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }
}

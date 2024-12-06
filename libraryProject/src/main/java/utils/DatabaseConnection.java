package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("Failed to load PostgreSQL JDBC driver", e);
//        }
//    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
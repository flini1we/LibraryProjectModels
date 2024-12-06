package repositories;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements repositories.interfaces.UserRepository {
    private final Connection connection;

    private static final String INSERT_USER = "INSERT INTO users (username, password, email, first_name, last_name, role, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password = ?, email = ?, first_name = ?, last_name = ?, role = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUser(User user) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getRole());
            stmt.setTimestamp(7, Timestamp.valueOf(user.getRegistrationDate()));
            stmt.executeUpdate();
        }
    }

    @Override
    public User getUserById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_USER_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToUser(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            while (rs.next()) {
                users.add(mapToUser(rs));
            }
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_USER)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapToUser(rs);
                }
            }
        }
        return null;
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setRole(rs.getString("role"));
        user.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
        return user;
    }
}
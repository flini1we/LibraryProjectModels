package repositories.interfaces;

import models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void createUser(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(int id) throws SQLException;

    User findByUsername(String username) throws SQLException;
}
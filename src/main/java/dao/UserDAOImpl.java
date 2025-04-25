package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.UserDAO;
import model.User;

public class UserDAOImpl implements UserDAO{
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (email, first_name, last_name, password, gender, favorite_color, date_of_birth, created_at, updated_at, role, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getFavoriteColor());
            statement.setObject(7, user.getDateOfBirth());
            statement.setObject(8, user.getCreatedAt());
            statement.setObject(9, user.getUpdatedAt());
            statement.setString(10, user.getRole());
            statement.setBoolean(11, user.isActive());
            statement.executeUpdate();
        }
    }

    // READ ALL
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("password"),
                    resultSet.getString("gender"),
                    resultSet.getString("favorite_color"),
                    resultSet.getObject("date_of_birth", LocalDate.class),
                    resultSet.getObject("created_at", LocalDateTime.class),
                    resultSet.getObject("updated_at", LocalDateTime.class),
                    resultSet.getString("role"),
                    resultSet.getBoolean("is_active")
                );
                users.add(user);
            }
        }
        return users;
    }

    // READ BY ID
    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password"),
                        resultSet.getString("gender"),
                        resultSet.getString("favorite_color"),
                        resultSet.getObject("date_of_birth", LocalDate.class),
                        resultSet.getObject("created_at", LocalDateTime.class),
                        resultSet.getObject("updated_at", LocalDateTime.class),
                        resultSet.getString("role"),
                        resultSet.getBoolean("is_active")
                    );
                }
            }
        }
        return null;
    }

    // READ BY EMAIL
    public List<User> getUsersByEmail(String email) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE email Like ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password"),
                        resultSet.getString("gender"),
                        resultSet.getString("favorite_color"),
                        resultSet.getObject("date_of_birth", LocalDate.class),
                        resultSet.getObject("created_at", LocalDateTime.class),
                        resultSet.getObject("updated_at", LocalDateTime.class),
                        resultSet.getString("role"),
                        resultSet.getBoolean("is_active")
                    );
                users.add(user);
            }
        }
        return users;
        }
    }

    // UPDATE
    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE users SET email = ?, first_name = ?, last_name = ?, password = ?, gender = ?, favorite_color = ?, date_of_birth = ?, created_at = ?, updated_at = ?, role = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getFavoriteColor());
            statement.setObject(7, user.getDateOfBirth());
            statement.setObject(8, user.getCreatedAt());
            statement.setObject(9, user.getUpdatedAt());
            statement.setString(10, user.getRole());
            statement.setBoolean(11, user.isActive());
            statement.setInt(12, id);
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
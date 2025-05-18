package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.UserDAO;
import model.User;
import utils.DateTimeParser;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (email, first_name, last_name, password, phone, postal_code, address_line1, address_line2, date_of_birth, payment_method, created_at, updated_at, role, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParams(statement, user);
            statement.executeUpdate();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getUsersByEmail(String email) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE email LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE users SET email = ?, first_name = ?, last_name = ?, password = ?, phone = ?, postal_code = ?, address_line1 = ?, address_line2 = ?, date_of_birth = ?, payment_method = ?, created_at = ?, updated_at = ?, role = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParams(statement, user);
            statement.setInt(15, id); // 14개 필드 + id
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // ResultSet에서 User 객체로 매핑
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getString("phone"),
                rs.getString("postal_code"),
                rs.getString("address_line1"),
                rs.getString("address_line2"),
                rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null,
                rs.getString("payment_method"),
                DateTimeParser.parseLocalDateTime(rs.getString("created_at")),
                DateTimeParser.parseLocalDateTime(rs.getString("updated_at")),
                rs.getString("role"),
                rs.getBoolean("is_active")
        );
    }

    // User 객체의 값을 PreparedStatement에 세팅
    private void setUserParams(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getPhone());
        statement.setString(6, user.getPostalCode());
        statement.setString(7, user.getAddressLine1());
        statement.setString(8, user.getAddressLine2());
        statement.setDate(9, user.getDateOfBirth() != null ? java.sql.Date.valueOf(user.getDateOfBirth()) : null);
        statement.setString(10, user.getPaymentMethod());
        statement.setString(11, DateTimeParser.toText(user.getCreatedAt()));
        statement.setString(12, DateTimeParser.toText(user.getUpdatedAt()));
        statement.setString(13, user.getRole());
        statement.setBoolean(14, user.isActive());
    }
}

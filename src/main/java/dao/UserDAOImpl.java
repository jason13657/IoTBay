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
        String query = "INSERT INTO users (email, first_name, last_name, password, gender, favorite_color, date_of_birth, created_at, updated_at, role, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE users SET email = ?, first_name = ?, last_name = ?, password = ?, gender = ?, favorite_color = ?, date_of_birth = ?, created_at = ?, updated_at = ?, role = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParams(statement, user);
            statement.setInt(12, id); // Only the last param is different
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

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("password"),
            rs.getString("gender"),
            rs.getString("favorite_color"),
            DateTimeParser.parseLocalDate(rs.getString("date_of_birth")),
            DateTimeParser.parseLocalDateTime(rs.getString("created_at")),
            DateTimeParser.parseLocalDateTime(rs.getString("updated_at")),
            rs.getString("role"),
            rs.getInt("is_active") != 0
        );
    }

    private void setUserParams(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getGender());
        statement.setString(6, user.getFavoriteColor());
        statement.setString(7, DateTimeParser.toText(user.getDateOfBirth()));
        statement.setString(8, DateTimeParser.toText(user.getCreatedAt()));
        statement.setString(9, DateTimeParser.toText(user.getUpdatedAt()));
        statement.setString(10, user.getRole());
        statement.setBoolean(11, user.isActive());
    }
}

//==================the code that Jungwook added ===================
@Override
    public boolean isEmailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        // 주문 취소 로직 추가 (트랜잭션 관리 필요)
        String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserQuery)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        }
    }
}
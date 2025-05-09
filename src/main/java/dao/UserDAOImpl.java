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
        String query = "INSERT INTO User (firstName, lastName, email, phoneNumber, dateOfBirth, role, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParams(statement, user, false);
            statement.executeUpdate();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
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
        String query = "SELECT * FROM User WHERE userID = ?";
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
        String query = "SELECT * FROM User WHERE email LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + email + "%");
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
        String query = "SELECT * FROM User WHERE email = ?";
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
    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE User SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, dateOfBirth = ?, role = ?, updatedAt = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setUserParams(statement, user, true);
            statement.setInt(8, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM User WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("userID"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("email"),
            rs.getString("phoneNumber"),
            DateTimeParser.parseLocalDate(rs.getString("dateOfBirth")),
            rs.getString("role"),
            DateTimeParser.parseLocalDateTime(rs.getString("createdAt")),
            DateTimeParser.parseLocalDateTime(rs.getString("updatedAt"))
        );
    }

     /**
     * @param statement PreparedStatement
     * @param user      User object
     * @param isUpdate  true if for UPDATE (skip createdAt)
     */
    private void setUserParams(PreparedStatement statement, User user, boolean isUpdate) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, DateTimeParser.toText(user.getDateOfBirth()));
        statement.setString(6, user.getRole());
        if (isUpdate) {
            statement.setString(7, DateTimeParser.toText(user.getUpdatedAt()));
        } else {
            statement.setString(7, DateTimeParser.toText(user.getCreatedAt()));
            statement.setString(8, DateTimeParser.toText(user.getUpdatedAt()));
        }
    }
}
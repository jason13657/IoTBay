package dao;

import model.AccessLog;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccessLogDAOImpl {
    private final Connection connection;

    public AccessLogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_log (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, log.getUserId());
            statement.setString(2, log.getAction());
            statement.setObject(3, log.getTimestamp());
            statement.executeUpdate();
        }
    }

    // READ: Get access log by ID
    public AccessLog getAccessLogById(int id) throws SQLException {
        String query = "SELECT log_id, user_id, action, timestamp FROM access_log WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new AccessLog(
                        rs.getInt("log_id"),
                        rs.getInt("user_id"),
                        rs.getString("action"),
                        rs.getObject("timestamp", LocalDateTime.class)
                    );
                }
            }
        }
        return null;
    }

    // READ: Get all logs for a user
    public List<AccessLog> getAccessLogsByUserId(int userId) throws SQLException {
        String query = "SELECT log_id, user_id, action, timestamp FROM access_log WHERE user_id = ?";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    logs.add(new AccessLog(
                        rs.getInt("log_id"),
                        rs.getInt("user_id"),
                        rs.getString("action"),
                        rs.getObject("timestamp", LocalDateTime.class)
                    ));
                }
            }
        }
        return logs;
    }

    // DELETE: Remove log by ID
    public void deleteAccessLog(int id) throws SQLException {
        String query = "DELETE FROM access_log WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // DELETE: Remove all logs for a user (optional)
    public void deleteLogsByUserId(int userId) throws SQLException {
        String query = "DELETE FROM access_log WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
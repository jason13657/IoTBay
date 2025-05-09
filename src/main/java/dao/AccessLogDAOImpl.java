package dao;

import model.AccessLog;
import utils.DateTimeParser;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AccessLogDAO;

public class AccessLogDAOImpl implements AccessLogDAO {
    private final Connection connection;

    public AccessLogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setAccessLogParams(statement, log);
            statement.executeUpdate();
        }
    }

    @Override
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        String query = "SELECT id, user_id, action, timestamp FROM access_logs";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                logs.add(mapResultSetToAccessLog(rs));
            }
        }
        return logs;
    }

    @Override
    public AccessLog getAccessLogById(int id) throws SQLException {
        String query = "SELECT id, user_id, action, timestamp FROM access_logs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccessLog(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<AccessLog> getAccessLogsByUserId(int userId) throws SQLException {
        String query = "SELECT id, user_id, action, timestamp FROM access_logs WHERE user_id = ?";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAccessLog(rs));
                }
            }
        }
        return logs;
    }

    @Override
public List<AccessLog> getAccessLogsByUserIdAndDate(int userId, LocalDate date) throws Exception {
    List<AccessLog> logs = new ArrayList<>();
    String sql = "SELECT * FROM AccessLog WHERE userID = ? AND DATE(timestamp) = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.setDate(2, java.sql.Date.valueOf(date));
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                logs.add(new AccessLog(
                    rs.getInt("logID"),
                    rs.getInt("userID"),
                    rs.getString("action"),
                    rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        }
    }
    return logs;
}


    @Override
    public void deleteAccessLog(int id) throws SQLException {
        String query = "DELETE FROM access_logs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void addAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, log.getUserId());
            statement.setString(2, log.getAction());
            statement.setString(3, DateTimeParser.toText(log.getTimestamp())); // Serialize LocalDateTime to text
            statement.executeUpdate();
        }
    }

    private void setAccessLogParams(PreparedStatement statement, AccessLog log) throws SQLException {
        statement.setInt(1, log.getUserId());
        statement.setString(2, log.getAction());
        statement.setString(3, DateTimeParser.toText(log.getTimestamp())); // Serialize LocalDateTime to text
    }

    private AccessLog mapResultSetToAccessLog(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("action"),
            DateTimeParser.parseLocalDateTime(rs.getString("timestamp")) // Parse text to LocalDateTime
        );
    }
}
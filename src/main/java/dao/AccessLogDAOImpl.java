package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AccessLogDAO;
import model.AccessLog;

public class AccessLogDAOImpl implements AccessLogDAO {
    private final Connection connection;

    public AccessLogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, log.getUserId());
            statement.setString(2, log.getAction());
            // LocalDateTime을 ISO-8601 문자열로 저장 (예: 2025-05-20T14:23:45)
            statement.setString(3, log.getTimestamp().toString());
            statement.executeUpdate();
        }
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
        String query = "SELECT id, user_id, action, timestamp FROM access_logs WHERE user_id = ? ORDER BY timestamp DESC";
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
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        String query = "SELECT id, user_id, action, timestamp FROM access_logs ORDER BY timestamp DESC";
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
    public void deleteAccessLog(int id) throws SQLException {
        String query = "DELETE FROM access_logs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<AccessLog> getAccessLogsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        String query = "SELECT id, user_id, action, timestamp FROM access_logs WHERE user_id = ? AND timestamp >= ? AND timestamp < ? ORDER BY timestamp DESC";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            // LocalDate를 LocalDateTime 자정으로 변환 후 문자열로 저장
            statement.setString(2, startDate.atStartOfDay().toString());
            statement.setString(3, endDate.plusDays(1).atStartOfDay().toString());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAccessLog(rs));
                }
            }
        }
        return logs;
    }

    // ResultSet -> AccessLog 객체 변환
    private AccessLog mapResultSetToAccessLog(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("action"),
            LocalDateTime.parse(rs.getString("timestamp")) // TEXT → LocalDateTime
        );
    }
}

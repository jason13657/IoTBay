package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
            statement.setTimestamp(3, Timestamp.valueOf(log.getTimestamp()));
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
            // 시작일은 자정, 종료일은 다음날 자정(미포함)까지
            statement.setTimestamp(2, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(3, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));
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
            rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}

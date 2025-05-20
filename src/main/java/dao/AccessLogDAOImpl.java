package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AccessLogDAO;
import model.AccessLog;

public class AccessLogDAOImpl implements AccessLogDAO {
    private final Connection connection;
    // 포맷 상수
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // yyyy-MM-dd'T'HH:mm:ss
    private static final DateTimeFormatter SPACE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AccessLogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, log.getUserId());
            statement.setString(2, log.getAction());
            // 항상 ISO-8601 포맷으로 저장
            statement.setString(3, log.getTimestamp().format(ISO_FORMATTER));
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
            // ISO-8601 포맷으로 비교
            statement.setString(2, startDate.atStartOfDay().format(ISO_FORMATTER));
            statement.setString(3, endDate.plusDays(1).atStartOfDay().format(ISO_FORMATTER));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAccessLog(rs));
                }
            }
        }
        return logs;
    }

    // ResultSet -> AccessLog 객체 변환 (포맷 자동 인식)
    private AccessLog mapResultSetToAccessLog(ResultSet rs) throws SQLException {
        String ts = rs.getString("timestamp");
        LocalDateTime timestamp;
        try {
            // ISO-8601 ("2025-05-20T14:23:45")
            timestamp = LocalDateTime.parse(ts, ISO_FORMATTER);
        } catch (Exception e) {
            // 공백 구분 ("2025-05-20 14:23:45")
            timestamp = LocalDateTime.parse(ts, SPACE_FORMATTER);
        }
        return new AccessLog(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("action"),
            timestamp
        );
    }
}

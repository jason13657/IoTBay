package dao;

import model.AccessLog;
import dao.interfaces.AccessLogDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccessLogDAOImpl implements AccessLogDAO {
    private final Connection connection;

    public AccessLogDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // --- 기존 메서드 전체 수정 ---
    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        // 더미 구현 (필요 시 확장)
    }

    @Override
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        String query = "SELECT log_id, user_id, login_time, logout_time, ip_address FROM access_logs";
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
        String query = "SELECT * FROM access_logs WHERE log_id = ?";
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
        String query = "SELECT * FROM access_logs WHERE user_id = ?";
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
    public void deleteAccessLog(int id) throws SQLException {
        String query = "DELETE FROM access_logs WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // --- Jungwook 추가 메서드 수정 ---
    @Override
    public int createLoginLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, login_time, ip_address) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, log.getUserId());
            statement.setTimestamp(2, log.getLoginTime());
            statement.setString(3, log.getIpAddress());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public void updateLogoutTime(int logId, Date logoutTime) throws SQLException {
        String query = "UPDATE access_logs SET logout_time = ? WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, new Timestamp(logoutTime.getTime()));
            statement.setInt(2, logId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<AccessLog> findByUserIdAndDate(int userId, Date searchDate) throws SQLException {
        String query = "SELECT * FROM access_logs WHERE user_id = ? AND DATE(login_time) = ?";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDate(2, new java.sql.Date(searchDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAccessLog(rs));
                }
            }
        }
        return logs;
    }

    // --- 공통 유틸리티 메서드 ---
    private AccessLog mapResultSetToAccessLog(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("log_id"),
            rs.getInt("user_id"),
            rs.getTimestamp("login_time"),
            rs.getTimestamp("logout_time"),
            rs.getString("ip_address")
        );
    }
}
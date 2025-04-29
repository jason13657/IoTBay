package dao;

import model.AccessLog;
import utils.DateTimeParser;

import java.sql.*;
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
    public void deleteAccessLog(int id) throws SQLException {
        String query = "DELETE FROM access_logs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
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


//=========The code Jungwook added to the end of AccessLogDAOImpl.java=========

@Override
    public int createLoginLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, login_time, ip_address) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, log.getUserId());
            statement.setTimestamp(2, new Timestamp(log.getLoginTime().getTime()));
            statement.setString(3, log.getIpAddress());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1); // 생성된 log_id 반환
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
                    logs.add(new AccessLog(
                        rs.getInt("log_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("login_time"),
                        rs.getTimestamp("logout_time"),
                        rs.getString("ip_address")
                    ));
                }
            }
        }
        return logs;
    }
}

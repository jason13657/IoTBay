package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    // 1. 로그인 로그 생성(로그인 시점, log_id 반환)
    // 이 메서드는 로그인 시점에 호출되어야 하며, 로그아웃 시점은 null로 설정됩니다.
    // 로그아웃 시점은 updateLogoutTime 메서드를 사용하여 업데이트합니다.
    // create access log with login time and return the generated log_id
    // This method is used to create a login log and return the generated log_id   

    public int createLoginLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, login_time, ip_address) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, log.getUserId());
            statement.setTimestamp(2, log.getLoginTime());
            statement.setString(3, log.getIpAddress());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 log_id 반환
                }
            }
        }
        throw new SQLException("로그인 로그 생성 실패");
    }

    // 2. 로그아웃 시 로그아웃 시간 업데이트
    public void updateLogoutTime(int logId, Timestamp logoutTime) throws SQLException {
        String query = "UPDATE access_logs SET logout_time = ? WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, logoutTime);
            statement.setInt(2, logId);
            statement.executeUpdate();
        }
    }

    // 기존 createAccessLog (원한다면 유지)
    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        String query = "INSERT INTO access_logs (user_id, login_time, logout_time, ip_address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, log.getUserId());
            statement.setTimestamp(2, log.getLoginTime());
            statement.setTimestamp(3, log.getLogoutTime());
            statement.setString(4, log.getIpAddress());
            statement.executeUpdate();
        }
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
        String sql = "SELECT * FROM access_logs WHERE user_id = ? ORDER BY login_time DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<AccessLog> logs = new ArrayList<>();
            while (rs.next()) {
                logs.add(map(rs));
            }
            return logs;
        }
    }


    @Override
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        String query = "SELECT * FROM access_logs";
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
        String query = "DELETE FROM access_logs WHERE log_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // ResultSet에서 AccessLog 객체로 변환하는 메서드
    private AccessLog mapResultSetToAccessLog(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("log_id"),
            rs.getInt("user_id"),
            rs.getTimestamp("login_time"),
            rs.getTimestamp("logout_time"),
            rs.getString("ip_address")
        );
    }

    public List<AccessLog> findByUserIdAndDate(int userId, java.sql.Date date) throws SQLException {
        String query = "SELECT * FROM access_logs WHERE user_id = ? AND DATE(login_time) = ?";
        List<AccessLog> logs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDate(2, date);
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
        String sql = "SELECT * FROM access_logs WHERE user_id = ? AND DATE(login_time) = ? ORDER BY login_time DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            List<AccessLog> logs = new ArrayList<>();
            while (rs.next()) {
                logs.add(map(rs));
            }
            return logs;
        }
    }

    private AccessLog map(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getTimestamp("login_time"),
            rs.getTimestamp("logout_time"),
            rs.getString("ip_address")  
            // ← 이 필드도 필요함
        );
    }
    
}

// 로그아웃 시 로그아웃 시간 업데이트
// public void updateLogoutTime(int userId, Timestamp logoutTime) throws SQLException {
//     // 가장 마지막 로그인 로그의 로그아웃 시간만 갱신
//     for (int i = logs.size() - 1; i >= 0; i--) {
//         AccessLog log = logs.get(i);
//         if (log.getUserId() == userId && log.getLogoutTime() == null) {
//             log.setLogoutTime(logoutTime);
//             break;
//         }
//     }
// }


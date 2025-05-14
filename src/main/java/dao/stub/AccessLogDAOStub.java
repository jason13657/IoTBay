package dao.stub;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AccessLogDAO;
import model.AccessLog;

public class AccessLogDAOStub implements AccessLogDAO {
    private final List<AccessLog> logs = new ArrayList<>();

    public AccessLogDAOStub() {
        // 예시 초기 데이터

        //Example data for testing 
        logs.add(new AccessLog(1, 1, Timestamp.valueOf("2024-04-29 09:00:00"), null, "127.0.0.1"));
        logs.add(new AccessLog(2, 2, Timestamp.valueOf("2024-04-30 10:00:00"), Timestamp.valueOf("2024-04-30 12:00:00"), "192.168.0.2"));
    }

    private int getNextId() {
        return logs.isEmpty() ? 1 : logs.get(logs.size() - 1).getLogId() + 1;
    }

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        AccessLog newLog = new AccessLog(
            getNextId(),
            log.getUserId(),
            log.getLoginTime(),
            log.getLogoutTime(),
            log.getIpAddress()
        );
        logs.add(newLog);
    }

    @Override
    public AccessLog getAccessLogById(int id) throws SQLException {
        return logs.stream()
                .filter(log -> log.getLogId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AccessLog> getAccessLogsByUserId(int userId) throws SQLException {
        List<AccessLog> result = new ArrayList<>();
        for (AccessLog log : logs) {
            if (log.getUserId() == userId) {
                result.add(log);
            }
        }
        return result;
    }


    @Override
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        return new ArrayList<>(logs);
    }

    @Override
    public void deleteAccessLog(int id) throws SQLException {
        logs.removeIf(log -> log.getLogId() == id);
    }

    @Override
    public List<AccessLog> getAccessLogsByUserIdAndDate(int userId, LocalDate date) throws SQLException {
    List<AccessLog> result = new ArrayList<>();
    for (AccessLog log : logs) {
        if (log.getUserId() == userId && 
            log.getLoginTime() != null && 
            log.getLoginTime().toLocalDateTime().toLocalDate().equals(date)) {
            result.add(log);
        }
    }
    return result;
}
}





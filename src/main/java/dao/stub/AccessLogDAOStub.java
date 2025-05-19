package dao.stub;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AccessLogDAO;
import model.AccessLog;

public class AccessLogDAOStub implements AccessLogDAO {
    private final List<AccessLog> logs = new ArrayList<>();

    public AccessLogDAOStub() {
        // test data
        logs.add(new AccessLog(1, 1, "LOGIN", LocalDateTime.now().minusDays(1)));
        logs.add(new AccessLog(2, 2, "LOGOUT", LocalDateTime.now()));
        logs.add(new AccessLog(3, 1, "LOGIN", LocalDateTime.now().minusDays(3).withHour(8)));
        logs.add(new AccessLog(4, 1, "LOGOUT", LocalDateTime.now().minusDays(3).withHour(9)));
        logs.add(new AccessLog(5, 2, "LOGIN", LocalDateTime.now().minusDays(2).withHour(10)));
        logs.add(new AccessLog(6, 2, "LOGOUT", LocalDateTime.now().minusDays(2).withHour(11)));
        logs.add(new AccessLog(7, 3, "LOGIN", LocalDateTime.now().minusDays(4).withHour(7)));
        logs.add(new AccessLog(8, 3, "LOGOUT", LocalDateTime.now().minusDays(4).withHour(8)));
        logs.add(new AccessLog(9, 1, "LOGIN", LocalDateTime.now().minusDays(5).withHour(8)));
        logs.add(new AccessLog(10, 1, "LOGOUT", LocalDateTime.now().minusDays(5).withHour(9)));
        logs.add(new AccessLog(11, 2, "LOGIN", LocalDateTime.now().minusDays(6).withHour(10)));
        logs.add(new AccessLog(12, 2, "LOGOUT", LocalDateTime.now().minusDays(6).withHour(11)));
        logs.add(new AccessLog(13, 3, "LOGIN", LocalDateTime.now().minusDays(7).withHour(7)));
        logs.add(new AccessLog(14, 3, "LOGOUT", LocalDateTime.now().minusDays(7).withHour(8)));
        logs.add(new AccessLog(15, 1, "LOGIN", LocalDateTime.now().minusDays(8).withHour(8)));
        logs.add(new AccessLog(16, 1, "LOGOUT", LocalDateTime.now().minusDays(8).withHour(9)));
        logs.add(new AccessLog(17, 2, "LOGIN", LocalDateTime.now().minusDays(9).withHour(10)));
        logs.add(new AccessLog(18, 2, "LOGOUT", LocalDateTime.now().minusDays(9).withHour(11)));
        logs.add(new AccessLog(19, 3, "LOGIN", LocalDateTime.now().minusDays(10).withHour(7)));
        logs.add(new AccessLog(20, 3, "LOGOUT", LocalDateTime.now().minusDays(10).withHour(8)));
    }

    private int getNextId() {
        return logs.isEmpty() ? 1 : logs.get(logs.size() - 1).getId() + 1;
    }

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        AccessLog newLog = new AccessLog(
            getNextId(),
            log.getUserId(),
            log.getAction(),
            log.getTimestamp()
        );
        logs.add(newLog);
    }

    @Override
    public AccessLog getAccessLogById(int id) throws SQLException {
        return logs.stream()
                .filter(log -> log.getId() == id)
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
        logs.removeIf(log -> log.getId() == id);
    }

    @Override
    public List<AccessLog> getAccessLogsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<AccessLog> result = new ArrayList<>();
        for (AccessLog log : logs) {
            if (log.getUserId() == userId) {
                LocalDate logDate = log.getTimestamp().toLocalDate();
                if ((logDate.isEqual(startDate) || logDate.isAfter(startDate)) &&
                    (logDate.isEqual(endDate) || logDate.isBefore(endDate))) {
                    result.add(log);
                }
            }
        }
        return result;
    }
}

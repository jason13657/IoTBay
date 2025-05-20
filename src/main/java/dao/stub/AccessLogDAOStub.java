package dao.stub;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.AccessLog;
import dao.interfaces.AccessLogDAO;

public class AccessLogDAOStub implements AccessLogDAO {
    private final List<AccessLog> logs = new ArrayList<>();

    public AccessLogDAOStub() {
        // Example initial data
        logs.add(new AccessLog(1, 1, "LOGIN", LocalDateTime.now().minusDays(1)));
        logs.add(new AccessLog(2, 2, "LOGOUT", LocalDateTime.now()));
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
}
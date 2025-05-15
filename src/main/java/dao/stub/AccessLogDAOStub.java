package dao.stubs;

import dao.interfaces.AccessLogDAO;
import model.AccessLog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccessLogDAOStub implements AccessLogDAO {

    private final List<AccessLog> accessLogs = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public void createAccessLog(AccessLog log) throws SQLException {
        log.setId(idCounter++);
        accessLogs.add(log);
    }

    @Override
    public AccessLog getAccessLogById(int id) throws SQLException {
        return accessLogs.stream()
                .filter(log -> log.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AccessLog> getAccessLogsByUserId(int userId) throws SQLException {
        return accessLogs.stream()
                .filter(log -> log.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessLog> getAllAccessLogs() throws SQLException {
        return new ArrayList<>(accessLogs);
    }

    @Override
    public void deleteAccessLog(int id) throws SQLException {
        accessLogs.removeIf(log -> log.getId() == id);
    }

    @Override
    public List<AccessLog> getAccessLogsByUserIdAndDate(int userId, LocalDate date) throws Exception {
        return accessLogs.stream()
                .filter(log -> log.getUserId() == userId &&
                        log.getAccessTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void addAccessLog(AccessLog accessLog) throws SQLException {
        createAccessLog(accessLog); // Reuse createAccessLog method
    }
}

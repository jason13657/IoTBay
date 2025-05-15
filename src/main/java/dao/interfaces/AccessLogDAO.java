package dao.interfaces;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import model.AccessLog;

public interface AccessLogDAO {
    void createAccessLog(AccessLog log) throws SQLException;
    AccessLog getAccessLogById(int id) throws SQLException;
    List<AccessLog> getAccessLogsByUserId(int userId) throws SQLException;
    List<AccessLog> getAllAccessLogs() throws SQLException;
    void deleteAccessLog(int id) throws SQLException;
    //added method to get access logs by userId and date
    // This method is used to get access logs for a specific user on a specific date
    List<AccessLog> getAccessLogsByUserIdAndDate(int userId, LocalDate date) throws Exception;
    
    //added method to get access logs by userId and date
    void addAccessLog(AccessLog accessLog) throws SQLException;
}
package dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean isUserLoggedIn(int userId) {
        String sql = "SELECT action FROM access_log WHERE user_id = ? ORDER BY timestamp DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lastAction = rs.getString("action");
                return "login".equalsIgnoreCase(lastAction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void userLogin(int userId) {
        String sql = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, "login");
            stmt.setString(3, timestamp);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public void userLogout(int userId) {
        String sql = "INSERT INTO access_logs (user_id, action, timestamp) VALUES (?, ?, ?)";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, "logout");
            stmt.setString(3, timestamp);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newUser() {
        // add new user to usr table
    }
}

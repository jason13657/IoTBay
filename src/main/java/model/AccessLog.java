package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccessLog implements Serializable {
    private int id;
    private int userId;
    private String action;
    private LocalDateTime timestamp;

    public AccessLog(int id, int userId, String action, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.toString();
    }

    @Override
    public String toString() {
        return "AccessLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

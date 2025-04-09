package model;

import java.io.Serializable;

public class AccessLog implements Serializable {
    private int id;
    private int userId;
    private String action;
    private String timestamp;

    public AccessLog() {
    }

    public AccessLog(int id, int userId, String action, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.replace("T", " ").replace("Z", "");
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

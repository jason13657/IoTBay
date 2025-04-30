package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccessLog implements Serializable {
    private int logId;          // 필드명 변경 (id → logId)
    private int userId;
    private Timestamp loginTime;  // 추가
    private Timestamp logoutTime; // 추가
    private String ipAddress;     // 추가

    // 기본 생성자
    public AccessLog() {}

    // 전체 필드 생성자
    public AccessLog(int logId, int userId, Timestamp loginTime, Timestamp logoutTime, String ipAddress) {
        this.logId = logId;
        this.userId = userId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.ipAddress = ipAddress;
    }

    // Getter & Setter
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getLoginTime() { return loginTime; }
    public void setLoginTime(Timestamp loginTime) { this.loginTime = loginTime; }

    public Timestamp getLogoutTime() { return logoutTime; }
    public void setLogoutTime(Timestamp logoutTime) { this.logoutTime = logoutTime; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    // 포맷팅된 시간 반환 (옵션)
    public String getFormattedLoginTime() {
        return loginTime != null ? loginTime.toString() : "N/A";
    }

    public String getFormattedLogoutTime() {
        return logoutTime != null ? logoutTime.toString() : "Ongoing";
    }

    @Override
    public String toString() {
        return "AccessLog{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
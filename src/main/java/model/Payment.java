package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Payment implements Serializable{
    private final int id;
    private final int userId;
    private final int orderId;
    private LocalDateTime paymentDate;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(int id, int userId, int orderId, LocalDateTime paymentDate, double amount, String paymentMethod, String paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.paymentDate = paymentDate != null ? paymentDate : LocalDateTime.now(); 
        this.amount = amount;
        this.paymentMethod = paymentMethod != null ? paymentMethod : "UNKNOWN";
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PENDING";
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isPaymentSuccessful() {
        return "SUCCESS".equalsIgnoreCase(paymentStatus);
    }
}

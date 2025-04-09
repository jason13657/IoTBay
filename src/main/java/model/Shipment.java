package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Shipment implements Serializable{
    private final int id;
    private final int orderId;
    private String shippingAddress;
    private LocalDateTime shippingDate;
    private String shippingStatus;

    public Shipment(int id, int orderId, String shippingAddress, LocalDateTime shippingDate, String shippingStatus) {
        this.id = id;
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.shippingDate = shippingDate;
        this.shippingStatus = shippingStatus;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public boolean isDelivered() {
        return "Delivered".equalsIgnoreCase(shippingStatus);
    }
}

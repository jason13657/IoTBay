package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Shipment implements Serializable {
    private final int id;
    private final int orderId;
    private final int addressId;
    private LocalDateTime shippingDate;
    private String shippingStatus;

    public Shipment(int id, int orderId, int addressId, LocalDateTime shippingDate, String shippingStatus) {
        this.id = id;
        this.orderId = orderId;
        this.addressId = addressId;
        this.shippingDate = shippingDate;
        this.shippingStatus = shippingStatus;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getAddressId() {
        return addressId;
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

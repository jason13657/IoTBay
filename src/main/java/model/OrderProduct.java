package model;

import java.io.Serializable;

public class OrderProduct implements Serializable{
    private final int orderId;
    private final int productId;
    private int quantity;
    private double priceAtOrderTime;

    public OrderProduct(int orderId, int productId, int quantity, double priceAtOrderTime) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtOrderTime = priceAtOrderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(double priceAtOrderTime) {
        this.priceAtOrderTime = priceAtOrderTime;
    }

    public double getSubtotal() {
        return quantity * priceAtOrderTime;
    }
}

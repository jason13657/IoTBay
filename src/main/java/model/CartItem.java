package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CartItem implements Serializable {
    private final int userId;
    private final int productId;
    private int quantity;
    private double price; 
    private LocalDateTime addedAt;

    // Constructor
    public CartItem(int userId, int productId, int quantity, double price, LocalDateTime addedAt) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price; 
        this.addedAt = addedAt;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price; 
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Method to calculate subtotal
    public double getSubtotal(double price) {
        return price * quantity;
    }
}

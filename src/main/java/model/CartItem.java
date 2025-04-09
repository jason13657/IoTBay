package model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CartItem implements Serializable {
    private final int id;
    private int userId;
    private int productId;
    private int quantity;
    private LocalDateTime addedAt;

    // Constructor to initialize all fields
    public CartItem(int id, int userId, int productId, int quantity, LocalDateTime addedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
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

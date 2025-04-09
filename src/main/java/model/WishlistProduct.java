package model;

import java.io.Serializable;

public class WishlistProduct implements Serializable {
    private final int wishlistId;
    private final int productId;
    private int quantity;

    // Constructor
    public WishlistProduct(int wishlistId, int productId, int quantity) {
        this.wishlistId = wishlistId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getWishlistId() {
        return wishlistId;
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

    // toString method
    @Override
    public String toString() {
        return "WishlistProduct{" +
                "wishlistId=" + wishlistId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
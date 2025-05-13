package dao;

import model.WishlistProduct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistProductDao {
    private final Connection connection;

    public WishlistProductDao(Connection connection) {
        this.connection = connection;
    }

    // Create (Add product to wishlist)
    public void addWishlistProduct(WishlistProduct wishlistProduct) throws SQLException {
        String query = "INSERT INTO wishlist_product (wishlistID, productID, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, wishlistProduct.getWishlistId());
            statement.setInt(2, wishlistProduct.getProductId());
            statement.setInt(3, wishlistProduct.getQuantity());
            statement.executeUpdate();
        }
    }

    // Read (Get all products in a wishlist)
    public List<WishlistProduct> getWishlistProducts(int wishlistId) throws SQLException {
        String query = "SELECT wishlistID, productID, quantity FROM wishlist_product WHERE wishlistID = ?";
        List<WishlistProduct> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, wishlistId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(new WishlistProduct(
                        rs.getInt("wishlistID"),
                        rs.getInt("productID"),
                        rs.getInt("quantity")
                    ));
                }
            }
        }
        return products;
    }

    // Update quantity of product in wishlist
    public void updateWishlistProductQuantity(WishlistProduct wishlistProduct) throws SQLException {
        String query = "UPDATE wishlist_product SET quantity = ? WHERE wishlistID = ? AND productID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, wishlistProduct.getQuantity());
            statement.setInt(2, wishlistProduct.getWishlistId());
            statement.setInt(3, wishlistProduct.getProductId());
            statement.executeUpdate();
        }
    }

    // Delete product from wishlist
    public void deleteWishlistProduct(int wishlistId, int productId) throws SQLException {
        String query = "DELETE FROM wishlist_product WHERE wishlistID = ? AND productID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, wishlistId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
    }
}
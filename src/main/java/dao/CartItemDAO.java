package dao;

import model.CartItem;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private final Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Add item to cart (with price)
    public void addCartItem(CartItem cartItem) throws SQLException {
        String query = "INSERT INTO cart_items (user_id, product_id, quantity, price, added_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItem.getUserId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.setDouble(4, cartItem.getPrice());   // set price here
            statement.setString(5, cartItem.getAddedAt().toString());
            statement.executeUpdate();
        }
    }

    // READ: Get all items in a user's cart (include price)
    public List<CartItem> getCartItemsByUserId(int userId) throws SQLException {
        String query = "SELECT user_id, product_id, quantity, price, added_at FROM cart_items WHERE user_id = ?";
        List<CartItem> cartItems = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        LocalDateTime.parse(rs.getString("added_at"))
                    ));
                }
            }
        }
        return cartItems;
    }

    // READ: Get specific item in user's cart (include price)
    public CartItem getCartItem(int userId, int productId) throws SQLException {
        String query = "SELECT user_id, product_id, quantity, price, added_at FROM cart_items WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        LocalDateTime.parse(rs.getString("added_at"))
                    );
                }
            }
        }
        return null;
    }

    // UPDATE: Update quantity of a product in the user's cart (price stays the same)
    public void updateCartItemQuantity(int userId, int productId, int quantity) throws SQLException {
        String query = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        }
    }

    // DELETE: Remove a specific product from the user's cart
    public void deleteCartItem(int userId, int productId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL: Clear cart for a specific user
    public void clearCartByUserId(int userId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}

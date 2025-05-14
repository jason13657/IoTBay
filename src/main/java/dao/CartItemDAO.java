package dao;

import model.CartItem;
<<<<<<< HEAD

=======
>>>>>>> feature/userAccessManage
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
<<<<<<< HEAD
    private final Connection conn;

    public CartItemDAO(Connection conn) {
        this.conn = conn;
    }

    public List<CartItem> getCartItemsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";
        List<CartItem> items = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("added_at").toLocalDateTime()
                );
                items.add(item);
            }
        }

        return items;
    }

    public void addCartItem(CartItem item) throws SQLException {
        String sql = "INSERT INTO cart_items (user_id, product_id, quantity, added_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getUserId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setTimestamp(4, Timestamp.valueOf(item.getAddedAt()));
            stmt.executeUpdate();
        }
    }

    public void deleteCartItem(int id) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
=======
    private final Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Add item to cart (using user_id and product_id as composite key)
    public void addCartItem(CartItem cartItem) throws SQLException {
        String query = "INSERT INTO cart_item (user_id, product_id, quantity, added_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItem.getUserId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.setObject(4, cartItem.getAddedAt());  // Works with LocalDateTime (JDBC 4.2+)
            statement.executeUpdate();
        }
    }

    // READ: Get all items in a user's cart
    public List<CartItem> getCartItemsByUserId(int userId) throws SQLException {
        String query = "SELECT user_id, product_id, quantity, added_at FROM cart_item WHERE user_id = ?";
        List<CartItem> cartItems = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(new CartItem(
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getObject("added_at", LocalDateTime.class)
                    ));
                }
            }
        }
        return cartItems;
    }

    // UPDATE: Update quantity of a product in the user's cart
    public void updateCartItemQuantity(int userId, int productId, int quantity) throws SQLException {
        String query = "UPDATE cart_item SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        }
    }

    // DELETE: Remove a specific product from the user's cart
    public void deleteCartItem(int userId, int productId) throws SQLException {
        String query = "DELETE FROM cart_item WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
    }

    // DELETE ALL: Clear cart for a specific user
    public void clearCartByUserId(int userId) throws SQLException {
        String query = "DELETE FROM cart_item WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
>>>>>>> feature/userAccessManage

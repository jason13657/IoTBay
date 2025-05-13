package dao;

import model.CartItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
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

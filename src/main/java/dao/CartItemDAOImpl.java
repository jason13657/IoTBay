package dao;

import dao.interfaces.CartItemDAO;
import model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    private final Connection connection;

    public CartItemDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CartItem> getCartItemsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM cart_items WHERE user_id = ?";
        List<CartItem> cartItems = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(new CartItem(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("added_at").toLocalDateTime()
                    ));
                }
            }
        }
        return cartItems;
    }

    @Override
    public void addCartItem(CartItem cartItem) throws SQLException {
        String query = "INSERT INTO cart_items (user_id, product_id, quantity, added_at) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItem.getUserId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.setTimestamp(4, Timestamp.valueOf(cartItem.getAddedAt()));
            statement.executeUpdate();
        }
    }

    @Override
    public void updateCartItem(CartItem cartItem) throws SQLException {
        String query = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItem.getQuantity());
            statement.setInt(2, cartItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteCartItem(int cartItemId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        }
    }

    @Override
    public void clearCart(int userId) throws SQLException {
        String query = "DELETE FROM cart_items WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}

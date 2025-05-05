package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Wishlist;

public class WishlistDao {
    private final Connection connection;

    public WishlistDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createWishlist(Wishlist wishlist) throws SQLException {
        String query = "INSERT INTO wishlist (user_id, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, wishlist.getUserId());
            statement.setString(2, wishlist.getName());
            statement.setString(3, wishlist.getDescription());
            statement.executeUpdate();
        }
    }

    // READ by ID
    public Wishlist getWishlistById(int id) throws SQLException {
        String query = "SELECT * FROM wishlist WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Wishlist(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                    );
                }
            }
        }
        return null;
    }

    // READ by User ID (get first wishlist for a user)
    public Wishlist getWishlistByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM wishlist WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Wishlist(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateWishlist(Wishlist wishlist) throws SQLException {
        String query = "UPDATE wishlist SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, wishlist.getName());
            statement.setString(2, wishlist.getDescription());
            statement.setInt(3, wishlist.getId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deleteWishlist(int id) throws SQLException {
        String query = "DELETE FROM wishlist WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
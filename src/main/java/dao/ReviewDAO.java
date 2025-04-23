package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Review;

public class ReviewDAO {
    private final Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void createReview(Review review) throws SQLException {
        String query = "INSERT INTO reviews (user_id, product_id, rating, comment, reviewed_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getProductId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setObject(5, review.getReviewedAt());
            statement.executeUpdate();
        }
    }

    // Read
    public Review readReview(int id) throws SQLException {
        String query = "SELECT * FROM reviews WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        resultSet.getObject("reviewed_at", LocalDateTime.class)
                    );
                }
            }
        }
        return null;
    }

    public List<Review> readAllReviewsByProductId(int productId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE product_id = ?";
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(new Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        resultSet.getObject("reviewed_at", LocalDateTime.class)
                    ));
                }
            }
        }
        return reviews;
    }

    public List<Review> readAllReviews() throws SQLException {
        String query = "SELECT * FROM reviews";
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                reviews.add(new Review(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("product_id"),
                    resultSet.getInt("rating"),
                    resultSet.getString("comment"),
                    resultSet.getObject("reviewed_at", LocalDateTime.class)
                ));
            }
        }
        return reviews;
    }

    // Update
    public void updateReview(Review review) throws SQLException {
        String query = "UPDATE reviews SET rating = ?, comment = ?, reviewed_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComment());
            statement.setObject(3, review.getReviewedAt());
            statement.setInt(4, review.getId());
            statement.executeUpdate();
        }
    }

    // Delete
    public void deleteReview(int id) throws SQLException {
        String query = "DELETE FROM reviews WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}

package dao;

import model.PaymentDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDetailDAO {
    private final Connection connection;

    public PaymentDetailDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createPaymentDetail(PaymentDetail detail) throws SQLException {
        String query = "INSERT INTO payment_detail (user_id, card_holder_name, card_number, expiry_date, card_type, is_default) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detail.getUserId());
            statement.setString(2, detail.getCardHolderName());
            statement.setString(3, detail.getCardNumber());
            statement.setString(4, detail.getExpiryDate());
            statement.setString(5, detail.getCardType());
            statement.setBoolean(6, detail.isDefault());
            statement.executeUpdate();
        }
    }

    // READ: Get payment detail by ID
    public PaymentDetail getPaymentDetailById(int id) throws SQLException {
        String query = "SELECT * FROM payment_detail WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new PaymentDetail(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("card_holder_name"),
                        rs.getString("card_number"),
                        rs.getString("expiry_date"),
                        rs.getString("card_type"),
                        rs.getBoolean("is_default")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updatePaymentDetail(PaymentDetail detail) throws SQLException {
        String query = "UPDATE payment_detail SET card_holder_name = ?, card_number = ?, expiry_date = ?, card_type = ?, is_default = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, detail.getCardHolderName());
            statement.setString(2, detail.getCardNumber());
            statement.setString(3, detail.getExpiryDate());
            statement.setString(4, detail.getCardType());
            statement.setBoolean(5, detail.isDefault());
            statement.setInt(6, detail.getPaymentId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deletePaymentDetail(int id) throws SQLException {
        String query = "DELETE FROM payment_detail WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // GET all payment details for a user
    public List<PaymentDetail> getPaymentDetailsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM payment_detail WHERE user_id = ?";
        List<PaymentDetail> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    details.add(new PaymentDetail(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("card_holder_name"),
                        rs.getString("card_number"),
                        rs.getString("expiry_date"),
                        rs.getString("card_type"),
                        rs.getBoolean("is_default")
                    ));
                }
            }
        }
        return details;
    }
}
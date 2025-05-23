package dao;

import model.Payment;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createPayment(Payment payment) throws SQLException {
        String query = "INSERT INTO payment (user_id, order_id, payment_date, amount, detail_id, payment_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payment.getUserId());
            statement.setInt(2, payment.getOrderId());
            statement.setObject(3, payment.getPaymentDate());
            statement.setDouble(4, payment.getAmount());
            statement.setInt(5, payment.getPaymentMethod());
            statement.setString(6, payment.getPaymentStatus());
            statement.executeUpdate();
        }
    }

    // READ: Get payment by ID
    public Payment getPaymentById(int id) throws SQLException {
        String query = "SELECT * FROM payment WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("order_id"),
                        rs.getObject("payment_date", LocalDateTime.class),
                        rs.getDouble("amount"),
                        rs.getInt("detail_id"),
                        rs.getString("payment_status")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updatePayment(Payment payment) throws SQLException {
        String query = "UPDATE payment SET payment_date = ?, amount = ?, payment_status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, payment.getPaymentDate());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentStatus());
            statement.setInt(4, payment.getId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deletePayment(int id) throws SQLException {
        String query = "DELETE FROM payment WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // GET all payments by user
    public List<Payment> getPaymentsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM payment WHERE user_id = ?";
        List<Payment> payments = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("order_id"),
                        rs.getObject("payment_date", LocalDateTime.class),
                        rs.getDouble("amount"),
                        rs.getInt("detail_id"),
                        rs.getString("payment_status")
                    ));
                }
            }
        }
        return payments;
    }
}
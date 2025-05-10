package uts.isd.iotbay.dao;

import uts.isd.iotbay.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Assuming java.util.Date is used in Payment model for paymentDate
// If using java.sql.Date or Timestamp directly, adjust imports and types

public class PaymentDAO {

    private Connection conn;

    // Constructor that takes a connection - useful for managing connection lifecycle externally or testing
    public PaymentDAO(Connection connection) {
        this.conn = connection;
    }

    // Default constructor that could try to establish its own connection (less flexible for web apps)
    // For a web app, it's better to get the connection from a pool or a shared DBConnector instance
    // managed at a higher level (e.g., in a Servlet context listener or passed down).
    // For this example, we assume connection is passed in.

    /**
     * Creates a new payment record in the database.
     * @param payment The Payment object containing details to be saved.
     * @return The auto-generated paymentId if creation is successful, or -1 otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public int createPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payments (order_id, customer_id, payment_method, card_holder_name, " +
                     "card_number, card_expiry_date, card_cvv_encrypted, amount, payment_date, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, payment.getOrderId());
            pstmt.setInt(2, payment.getCustomerId());
            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setString(4, payment.getCardHolderName());
            pstmt.setString(5, payment.getCardNumber()); // Consider encryption before this step
            pstmt.setString(6, payment.getCardExpiryDate());
            pstmt.setString(7, payment.getCardCVV()); // Store encrypted CVV if at all. Best practice: DO NOT STORE CVV.
            pstmt.setDouble(8, payment.getAmount());
            // Assuming payment.getPaymentDate() returns java.util.Date
            // SQLite TEXT type for dates, so format as ISO8601 string or store as long (millis)
            // For simplicity here, we'll use toString() which is not ideal for date sorting/querying.
            // A more robust solution would use a SimpleDateFormat or store as milliseconds.
            if (payment.getPaymentDate() != null) {
                pstmt.setString(9, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payment.getPaymentDate()));
            } else {
                pstmt.setNull(9, Types.VARCHAR); // Or Types.TIMESTAMP if column type allows
            }
            pstmt.setString(10, payment.getPaymentStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated paymentId
                    }
                }
            }
        }
        return -1; // Indicate failure
    }

    /**
     * Retrieves a payment by its ID.
     * @param paymentId The ID of the payment to retrieve.
     * @return Payment object if found, null otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public Payment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE payment_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setCustomerId(rs.getInt("customer_id"));
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setCardHolderName(rs.getString("card_holder_name"));
                    payment.setCardNumber(rs.getString("card_number")); // Consider decryption if card number is encrypted
                    payment.setCardExpiryDate(rs.getString("card_expiry_date"));
                    // CVV should generally not be retrieved or stored. If it was stored (encrypted),
                    // decide if it needs to be decrypted and set here (usually not needed for display).
                    // payment.setCardCVV(rs.getString("card_cvv_encrypted")); 
                    payment.setAmount(rs.getDouble("amount"));
                    
                    // Handle date retrieval for SQLite TEXT type
                    String paymentDateStr = rs.getString("payment_date");
                    if (paymentDateStr != null) {
                        try {
                            payment.setPaymentDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDateStr));
                        } catch (java.text.ParseException e) {
                            // Log error or handle - date might be in unexpected format or null
                            System.err.println("Error parsing payment date: " + e.getMessage());
                            payment.setPaymentDate(null);
                        }
                    }
                    payment.setPaymentStatus(rs.getString("payment_status"));
                    return payment;
                }
            }
        }
        return null; // Not found
    }

    /**
     * Retrieves all payments for a specific customer.
     * @param customerId The ID of the customer.
     * @return A list of Payment objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<Payment> getPaymentsByCustomerId(int customerId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE customer_id = ? ORDER BY payment_date DESC"; // Order by date, newest first
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setCustomerId(rs.getInt("customer_id"));
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setCardHolderName(rs.getString("card_holder_name"));
                    payment.setCardNumber(rs.getString("card_number")); 
                    payment.setCardExpiryDate(rs.getString("card_expiry_date"));
                    // CVV is generally not set here
                    payment.setAmount(rs.getDouble("amount"));
                    
                    String paymentDateStr = rs.getString("payment_date");
                    if (paymentDateStr != null) {
                        try {
                            payment.setPaymentDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDateStr));
                        } catch (java.text.ParseException e) {
                            System.err.println("Error parsing payment date for customerId " + customerId + ": " + e.getMessage());
                            payment.setPaymentDate(null);
                        }
                    }
                    payment.setPaymentStatus(rs.getString("payment_status"));
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    /**
     * Retrieves payment(s) for a specific order.
     * @param orderId The ID of the order.
     * @return A list of Payment objects (though usually one payment per order, could be multiple attempts).
     * @throws SQLException if a database access error occurs.
     */
    public List<Payment> getPaymentsByOrderId(int orderId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE order_id = ? ORDER BY payment_date DESC"; // Usually one, but list allows for multiple attempts
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setCustomerId(rs.getInt("customer_id"));
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setCardHolderName(rs.getString("card_holder_name"));
                    payment.setCardNumber(rs.getString("card_number"));
                    payment.setCardExpiryDate(rs.getString("card_expiry_date"));
                    payment.setAmount(rs.getDouble("amount"));
                    
                    String paymentDateStr = rs.getString("payment_date");
                    if (paymentDateStr != null) {
                        try {
                            payment.setPaymentDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDateStr));
                        } catch (java.text.ParseException e) {
                            System.err.println("Error parsing payment date for orderId " + orderId + ": " + e.getMessage());
                            payment.setPaymentDate(null);
                        }
                    }
                    payment.setPaymentStatus(rs.getString("payment_status"));
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    /**
     * Searches for payments based on customer ID and optionally payment ID query or date query.
     * @param customerId The ID of the customer.
     * @param paymentIdQuery A string representing the payment ID to search for (exact match).
     * @param dateQuery The specific date (java.util.Date) for which to find payments. Date part will be matched.
     * @return A list of matching Payment objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<Payment> searchPayments(int customerId, String paymentIdQuery, java.util.Date dateQuery) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Payments WHERE customer_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(customerId);

        int paymentIdToSearch = -1;
        if (paymentIdQuery != null && !paymentIdQuery.trim().isEmpty()) {
            try {
                paymentIdToSearch = Integer.parseInt(paymentIdQuery.trim());
                sqlBuilder.append(" AND payment_id = ?");
                params.add(paymentIdToSearch);
            } catch (NumberFormatException e) {
                // If paymentIdQuery is not a valid integer, we might choose to ignore it or handle as an error.
                // For this example, if it's not a number, we won't add it to the query for payment_id.
                System.err.println("Payment ID query is not a valid number: " + paymentIdQuery);
            }
        }

        if (dateQuery != null) {
            // For SQLite, to match just the date part when dates are stored as YYYY-MM-DD HH:MM:SS text:
            // Use strftime('%Y-%m-%d', payment_date) = ?
            // or payment_date LIKE ? where the pattern is 'YYYY-MM-DD%'
            sqlBuilder.append(" AND date(payment_date) = date(?)"); // Compare date parts
            params.add(new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateQuery));
        }

        sqlBuilder.append(" ORDER BY payment_date DESC");

        try (PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
            int paramIndex = 1;
            for (Object param : params) {
                if (param instanceof Integer) {
                    pstmt.setInt(paramIndex++, (Integer) param);
                } else if (param instanceof String) {
                    pstmt.setString(paramIndex++, (String) param);
                }
                // Add other types if necessary
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setCustomerId(rs.getInt("customer_id"));
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setCardHolderName(rs.getString("card_holder_name"));
                    payment.setCardNumber(rs.getString("card_number"));
                    payment.setCardExpiryDate(rs.getString("card_expiry_date"));
                    payment.setAmount(rs.getDouble("amount"));
                    
                    String paymentDateStr = rs.getString("payment_date");
                    if (paymentDateStr != null) {
                        try {
                            payment.setPaymentDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentDateStr));
                        } catch (java.text.ParseException e) {
                            System.err.println("Error parsing payment date during search: " + e.getMessage());
                            payment.setPaymentDate(null);
                        }
                    }
                    payment.setPaymentStatus(rs.getString("payment_status"));
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    /**
     * Updates an existing payment record in the database.
     * @param payment The Payment object with updated details.
     * @return true if update is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean updatePayment(Payment payment) throws SQLException {
        String sql = "UPDATE Payments SET order_id = ?, customer_id = ?, payment_method = ?, " +
                     "card_holder_name = ?, card_number = ?, card_expiry_date = ?, " +
                     "card_cvv_encrypted = ?, amount = ?, payment_date = ?, payment_status = ? " +
                     "WHERE payment_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payment.getOrderId());
            pstmt.setInt(2, payment.getCustomerId());
            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setString(4, payment.getCardHolderName());
            pstmt.setString(5, payment.getCardNumber()); // Consider encryption
            pstmt.setString(6, payment.getCardExpiryDate());
            pstmt.setString(7, payment.getCardCVV()); // If updating CVV, ensure it's encrypted. Usually not re-stored.
            pstmt.setDouble(8, payment.getAmount());
            if (payment.getPaymentDate() != null) {
                pstmt.setString(9, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payment.getPaymentDate()));
            } else {
                pstmt.setNull(9, Types.VARCHAR);
            }
            pstmt.setString(10, payment.getPaymentStatus());
            pstmt.setInt(11, payment.getPaymentId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Deletes a payment record from the database.
     * @param paymentId The ID of the payment to delete.
     * @return true if deletion is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payments WHERE payment_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Checks if a payment can be modified or deleted based on its status or other business rules.
     * @param paymentId The ID of the payment to check.
     * @return true if the payment can be modified/deleted, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean checkPaymentCanBeModified(int paymentId) throws SQLException {
        // Example: Fetch payment status and check if it's "SAVED" or "PENDING_SUBMISSION"
        // SELECT payment_status FROM Payments WHERE payment_id = ?
        Payment payment = getPaymentById(paymentId);
        if (payment != null) {
            String status = payment.getPaymentStatus();
            return "SAVED".equalsIgnoreCase(status) || "PENDING_SUBMISSION".equalsIgnoreCase(status);
        }
        return false; // Placeholder or throw an exception if payment not found
    }

} 
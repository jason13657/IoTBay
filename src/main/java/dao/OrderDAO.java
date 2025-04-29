package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Order;

public class OrderDAO {
    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createOrder(Order order) throws SQLException {
        String query = "INSERT INTO \"order\" (user_id, order_date, status, total_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getUserId());
            statement.setObject(2, order.getOrderDate());
            statement.setString(3, order.getStatus());
            statement.setDouble(4, order.getTotalAmount());
            statement.executeUpdate();
        }
    }

    // READ: Get order by ID
    public Order getOrderById(int orderId) throws SQLException {
        String query = "SELECT order_id, user_id, order_date, status, total_amount FROM \"order\" WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getObject("order_date", LocalDateTime.class),
                        rs.getString("status"),
                        rs.getDouble("total_amount")
                    );
                }
            }
        }
        return null;
    }

    // READ: Get all orders by user ID
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        String query = "SELECT order_id, user_id, order_date, status, total_amount FROM \"order\" WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getObject("order_date", LocalDateTime.class),
                        rs.getString("status"),
                        rs.getDouble("total_amount")
                    ));
                }
            }
        }
        return orders;
    }

    // UPDATE: Update order status and total amount
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE \"order\" SET order_date = ?, status = ?, total_amount = ? WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, order.getOrderDate());
            statement.setString(2, order.getStatus());
            statement.setDouble(3, order.getTotalAmount());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        }
    }

    // DELETE: Delete order by ID
    public void deleteOrder(int orderId) throws SQLException {
        String query = "DELETE FROM \"order\" WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    public void cancelOrdersByUserId(int userId) throws SQLException {
        String query = "UPDATE \"order\" SET status = '취소됨' WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    public void cancelOrder(int orderId) throws SQLException {
        String query = "UPDATE \"order\" SET status = '취소됨' WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }
}
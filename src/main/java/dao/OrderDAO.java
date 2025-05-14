<<<<<<< HEAD
package dao;

<<<<<<< HEAD
import model.Order;

import java.sql.*;
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
>>>>>>> feature/userAccessManage
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class OrderDAO {
    private final Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public void saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, order_date, status, total_amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            stmt.setString(3, order.getStatus());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.executeUpdate();
        }
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getDouble("total_amount")
                );
                orders.add(order);
            }
        }

        return orders;
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
=======
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


    // the code jungwook added =========================

    public void cancelOrder(int orderId) throws SQLException {
        String query = "UPDATE \"order\" SET status = '취소됨' WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }
    //added by jungwook, cancel all orders when user delete account
    public void cancelAllOrdersByUserId(int userId) throws SQLException {
        String sql = "UPDATE orders SET status = 'CANCELLED' WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
>>>>>>> feature/userAccessManage
            stmt.executeUpdate();
        }
    }
}
<<<<<<< HEAD
=======

//=
>>>>>>> feature/userAccessManage
=======
>>>>>>> parent of ff88b35 (updated files)

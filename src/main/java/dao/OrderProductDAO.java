package dao;

import model.OrderProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDAO {
    private final Connection conn;

    public OrderProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void addOrderProduct(OrderProduct op) throws SQLException {
        String sql = "INSERT INTO order_products (order_id, product_id, quantity, price_at_order_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, op.getOrderId());
            stmt.setInt(2, op.getProductId());
            stmt.setInt(3, op.getQuantity());
            stmt.setDouble(4, op.getPriceAtOrderTime());
            stmt.executeUpdate();
        }
    }

    public List<OrderProduct> getProductsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_products WHERE order_id = ?";
        List<OrderProduct> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderProduct op = new OrderProduct(
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price_at_order_time")
                );
                list.add(op);
            }
        }

        return list;
    }
}

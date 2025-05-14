<<<<<<< HEAD
package dao;

import model.OrderProduct;
<<<<<<< HEAD

=======
>>>>>>> feature/userAccessManage
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDAO {
<<<<<<< HEAD
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
=======
    private final Connection connection;

    public OrderProductDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Add product to an order
    public void addOrderProduct(OrderProduct orderProduct) throws SQLException {
        String query = "INSERT INTO order_product (orderID, productID, quantity, priceAtOrderTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderProduct.getOrderId());
            stmt.setInt(2, orderProduct.getProductId());
            stmt.setInt(3, orderProduct.getQuantity());
            stmt.setDouble(4, orderProduct.getPriceAtOrderTime());
>>>>>>> feature/userAccessManage
            stmt.executeUpdate();
        }
    }

<<<<<<< HEAD
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
=======
    // READ: Get all products in an order
    public List<OrderProduct> getProductsByOrderId(int orderId) throws SQLException {
        String query = "SELECT orderID, productID, quantity, priceAtOrderTime FROM order_product WHERE orderID = ?";
        List<OrderProduct> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new OrderProduct(
                        rs.getInt("orderID"),
                        rs.getInt("productID"),
                        rs.getInt("quantity"),
                        rs.getDouble("priceAtOrderTime")
                    ));
                }
            }
        }
        return products;
    }

    // UPDATE: Update quantity and price (if needed) for a product in an order
    public void updateOrderProduct(OrderProduct orderProduct) throws SQLException {
        String query = "UPDATE order_product SET quantity = ?, priceAtOrderTime = ? WHERE orderID = ? AND productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderProduct.getQuantity());
            stmt.setDouble(2, orderProduct.getPriceAtOrderTime());
            stmt.setInt(3, orderProduct.getOrderId());
            stmt.setInt(4, orderProduct.getProductId());
            stmt.executeUpdate();
        }
    }

    // DELETE: Remove a product from an order
    public void deleteOrderProduct(int orderId, int productId) throws SQLException {
        String query = "DELETE FROM order_product WHERE orderID = ? AND productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    // DELETE: Remove all products from an order (optional)
    public void deleteAllProductsFromOrder(int orderId) throws SQLException {
        String query = "DELETE FROM order_product WHERE orderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }
}
>>>>>>> feature/userAccessManage
=======
>>>>>>> parent of ff88b35 (updated files)

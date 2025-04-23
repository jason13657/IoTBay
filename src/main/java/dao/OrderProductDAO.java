package dao;

import model.OrderProduct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDAO {
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
            stmt.executeUpdate();
        }
    }

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
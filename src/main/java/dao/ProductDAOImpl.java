package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.ProductDAO;
import model.Product;
import utils.DateTimeParser;


public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (category_id, name, description, price, stock_quantity, image_url, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setProductParams(statement, product);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        return products;
    }

    @Override
    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> getProductsByName(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name LIKE ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException {
        String query = "SELECT * FROM products WHERE category_id = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }

    @Override
    public void updateProduct(int id, Product product) throws SQLException {
        String query = "UPDATE products SET category_id = ?, name = ?, description = ?, price = ?, stock_quantity = ?, image_url = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setProductParams(statement, product);
            statement.setInt(8, id); // Only the last param is different
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getInt("category_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock_quantity"),
            rs.getString("image_url"),
            DateTimeParser.parseLocalDate(rs.getString("created_at"))
        );
    }

    private void setProductParams(PreparedStatement statement, Product product) throws SQLException {
        statement.setInt(1, product.getCategoryId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setDouble(4, product.getPrice());
        statement.setInt(5, product.getStockQuantity());
        statement.setString(6, product.getImageUrl());
        statement.setString(7, DateTimeParser.toText(product.getCreatedAt()));
    }
}
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


public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createProduct(Product product) throws SQLException {
        String query = "INSERT INTO product (category_id, name, description, price, stock_quantity, image_url, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getStockQuantity());
            statement.setString(6, product.getImageUrl());
            statement.setObject(7, product.getCreatedAt());
            statement.executeUpdate();
        }
    }

    // READ ALL products
    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getInt("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stock_quantity"),
                    resultSet.getString("image_url"),
                    resultSet.getObject("created_at", LocalDate.class)
                ));
            }
        }
        return products;
    }

    // READ products by name
    public List<Product> getProductsByName(String name) throws SQLException {
        String query = "SELECT * FROM product WHERE name LIKE ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getString("image_url"),
                        resultSet.getObject("created_at", LocalDate.class)
                    ));
                }
            }
        }
        return products;
    }

    // READ products by category ID
    public List<Product> getProductsByCategoryId(int categoryId) throws SQLException {
        String query = "SELECT * FROM product WHERE category_id = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getString("image_url"),
                        resultSet.getObject("created_at", LocalDate.class)
                    ));
                }
            }
        }
        return products;
    }

    // READ product by ID
    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                        resultSet.getInt("id"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getString("image_url"),
                        resultSet.getObject("created_at", LocalDate.class)
                    );
                }
            }
        }
        return null;
    }

    // UPDATE product
    public void updateProduct(int id, Product product) throws SQLException {
        String query = "UPDATE product SET category_id = ?, name = ?, description = ?, price = ?, stock_quantity = ?, image_url = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getStockQuantity());
            statement.setString(6, product.getImageUrl());
            statement.setObject(7, product.getCreatedAt());
            statement.setInt(8, id);
            statement.executeUpdate();
        }
    }

    // DELETE product by ID
    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
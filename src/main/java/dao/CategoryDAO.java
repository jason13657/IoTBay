package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO {
    private final Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createCategory(Category category) throws SQLException {
        String query = "INSERT INTO category (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
        }
    }

    // READ: Get category by ID
    public Category getCategoryById(int id) throws SQLException {
        String query = "SELECT category_id, name, description FROM category WHERE category_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                    );
                }
            }
        }
        return null;
    }

    // READ: Get all categories
    public List<Category> getAllCategories() throws SQLException {
        String query = "SELECT category_id, name, description FROM category";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(new Category(
                    resultSet.getInt("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
                ));
            }
        }
        return categories;
    }

    // UPDATE
    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE category SET name = ?, description = ? WHERE category_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deleteCategory(int id) throws SQLException {
        String query = "DELETE FROM category WHERE category_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
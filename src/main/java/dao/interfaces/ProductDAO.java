package dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import model.Product;

public interface ProductDAO {
    void createProduct(Product product) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    List<Product> getProductsByName(String name) throws SQLException;
    List<Product> getProductsByCategoryId(int categoryId) throws SQLException;
    Product getProductById(int id) throws SQLException;
    void updateProduct(int id, Product product) throws SQLException;
    void deleteProduct(int id) throws SQLException;
}
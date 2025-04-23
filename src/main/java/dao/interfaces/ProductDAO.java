package dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import model.Product;

public interface ProductDAO {
    void createProduct(Product product) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    Product getProductById(int id) throws SQLException;
    void updateProduct(Product product) throws SQLException;
    void deleteProduct(int id) throws SQLException;
}
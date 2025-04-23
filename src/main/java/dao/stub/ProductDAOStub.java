package dao.stub;

import java.time.LocalDate;
import java.util.ArrayList;
import dao.interfaces.ProductDAO;
import model.Product;


public class ProductDAOStub implements ProductDAO {
    ArrayList<Product> products = new ArrayList<>();

    public ProductDAOStub() {
        // Adding some dummy products for testing
        products.add(new Product(1, 101, "Product 1", "Description 1", 10.0, 50, "image1.jpg", LocalDate.now()));
        products.add(new Product(2, 102, "Product 2", "Description 2", 20.0, 30, "image2.jpg", LocalDate.now()));
        products.add(new Product(3, 103, "Product 3", "Description 3", 30.0, 20, "image3.jpg", LocalDate.now()));
    }

    @Override
    public void createProduct(Product product) {
        products.add(product);
    }
    @Override
    public ArrayList<Product> getAllProducts() {
        return products;
    }
    @Override
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }
    @Override
    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }
}

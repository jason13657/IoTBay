package dao.interfaces;

import model.CartItem; 
import java.sql.SQLException;
import java.util.List;

public interface CartItemDAO {
    List<CartItem> getCartItemsByUserId(int userId) throws SQLException;

    void addCartItem(CartItem cartItem) throws SQLException;

    void updateCartItem(CartItem cartItem) throws SQLException;

    void deleteCartItem(int cartItemId) throws SQLException;

    void clearCart(int userId) throws SQLException;
}

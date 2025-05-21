package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CartItemDAO;
import db.DBConnection;
import model.CartItem;
import model.User;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private CartItemDAO cartItemDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            cartItemDAO = new CartItemDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Integer userId;
        if (user != null) {
            userId = user.getId();
        } else {
            if (session.getAttribute("guestId") == null) {
                int guestId = -1 * new Random().nextInt(100000);
                session.setAttribute("guestId", guestId);
            }
            userId = (Integer) session.getAttribute("guestId");
        }

        String action = request.getParameter("action");
        try {
            if ("clear".equals(action)) {
                // Clear all cart items for this user
                cartItemDAO.clearCartByUserId(userId);

                response.sendRedirect("cart"); // Redirect to show empty cart
                return;
            }

            // Existing add to cart logic
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("productPrice"));

            CartItem existingItem = cartItemDAO.getCartItem(userId, productId);

            if (existingItem != null) {
                int updatedQuantity = existingItem.getQuantity() + quantity;
                cartItemDAO.updateCartItemQuantity(userId, productId, updatedQuantity);
            } else {
                CartItem newItem = new CartItem(userId, productId, quantity, price, LocalDateTime.now());
                cartItemDAO.addCartItem(newItem);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("product?productId=" + productId);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to process cart operation.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Integer userId;
        if (user != null) {
            userId = user.getId();
        } else {
            userId = (Integer) session.getAttribute("guestId");
        }

        try {
            List<CartItem> cartItems = cartItemDAO.getCartItemsByUserId(userId);
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load cart items");
        }
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartItemDAO;
import db.DBConnection;
import model.CartItem;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private CartItemDAO cartItemDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            cartItemDAO = new CartItemDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                Integer anonymousUserId = (Integer) request.getSession().getAttribute("anonymousUserId");
                if (anonymousUserId == null) {
                    anonymousUserId = (int) (Math.random() * -1000000);
                    request.getSession().setAttribute("anonymousUserId", anonymousUserId);
                }
                userId = anonymousUserId;
            }
            
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = 1; // you can fix quantity or get from form if you want
            LocalDateTime addedAt = LocalDateTime.now();
            
            CartItem existingItem = cartItemDAO.getCartItem(userId, productId);
            
            if (existingItem != null) {
                int newQuantity = existingItem.getQuantity() + quantity;
                cartItemDAO.updateCartItemQuantity(userId, productId, newQuantity);
            } else {
                CartItem newItem = new CartItem(userId, productId, quantity, addedAt);
                cartItemDAO.addCartItem(newItem);
            }
            
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Item added to cart\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}

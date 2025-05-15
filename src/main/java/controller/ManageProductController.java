package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.ProductDAOImpl;
import dao.interfaces.ProductDAO;
import db.DBConnection;
import model.Product;
import model.User;

@WebServlet("/api/manage/products") 
public class ManageProductController extends HttpServlet {
    private ProductDAO productDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            productDAO = new ProductDAOImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        String idParam = request.getParameter("id");
        String nameParam = request.getParameter("name");
        String categoryIdParam = request.getParameter("categoryId");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Product product = productDAO.getProductById(id);
                if (product != null) {
                    response.getWriter().write(gson.toJson(product));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Product not found\"}");
                }
            } else if (nameParam != null) { 
                List<Product> products = productDAO.getProductsByName(nameParam);
                response.getWriter().write(gson.toJson(products));
            } else if (categoryIdParam != null) { 
                int categoryId = Integer.parseInt(categoryIdParam);
                List<Product> products = productDAO.getProductsByCategoryId(categoryId);
                response.getWriter().write(gson.toJson(products));
            } else { 
                List<Product> products = productDAO.getAllProducts();
                response.getWriter().write(gson.toJson(products));
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        try {
            BufferedReader reader = request.getReader();
            Product product = gson.fromJson(reader, Product.class);
            productDAO.createProduct(product);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(product));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            BufferedReader reader = request.getReader();
            Product product = gson.fromJson(reader, Product.class);
            productDAO.updateProduct(id, product);
            response.getWriter().write("{\"message\": \"Product updated successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(id);
            response.getWriter().write("{\"message\": \"Product deleted successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) return false;

        User user = (User) userObj;
        return "admin".equalsIgnoreCase(user.getRole());
    }
}
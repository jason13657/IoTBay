package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

import db.DBConnection;
import dao.ProductDAO;
import model.Product;

    
import java.util.ArrayList;
import java.util.List;



public class ManageProductController extends HttpServlet {
    private ProductDAO productDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            productDAO = new ProductDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize ProductDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Product> products = productDAO.getAllProducts();
            String json = gson.toJson(products);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }
}
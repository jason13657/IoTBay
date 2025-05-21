package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ProductDAOImpl;
import dao.interfaces.ProductDAO;
import db.DBConnection;
import model.Product;

@WebServlet("/search")
public class BrowseProductController extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            productDAO = new ProductDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("query");
        String categoryIdParam = request.getParameter("categoryId");
        List<Product> products;

        try {
            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
            boolean hasCategory = categoryIdParam != null && !categoryIdParam.trim().isEmpty();

            if (hasCategory) {
                int categoryId = Integer.parseInt(categoryIdParam);
                products = productDAO.getProductsByCategoryId(categoryId);

                if (hasKeyword) {
                    String lowerKeyword = keyword.trim().toLowerCase();
                    products = products.stream()
                            .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                            .collect(Collectors.toList());
                }

                request.setAttribute("categoryId", categoryId);
            } else if (hasKeyword) {
                products = productDAO.getProductsByName(keyword.trim());
            } else {
                products = productDAO.getAllProducts();
            }

            request.setAttribute("results", products);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("browse.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid category ID\"}");
        }
    }
}

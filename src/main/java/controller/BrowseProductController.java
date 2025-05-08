package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        } catch (SQLException e) {
            throw new RuntimeException("Failed to init");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to init");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("query");


        try {
            List<Product> products = productDAO.getProductsByName(keyword);
            request.setAttribute("results", products);
            request.setAttribute("keyword", keyword);

            try {
                request.getRequestDispatcher("browse.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
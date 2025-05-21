package controller;

import dao.CartItemDAO;
import dao.OrderDAO;
import db.DBConnection;
import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orderhistory")
public class OrderHistoryController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            orderDAO = new OrderDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Redirect to login if user is not logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Fetch orders for the logged-in user
            List<Order> orders = orderDAO.getOrdersByUserId(user.getId());


            // Set orders as request attribute and forward to JSP
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("orderList.jsp").forward(request, response);

        } catch (SQLException e) {
            // Handle SQL exceptions
            throw new ServletException("Error retrieving orders for user " + user.getId(), e);
        }
    }
}

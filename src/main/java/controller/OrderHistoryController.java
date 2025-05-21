package controller;

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
import java.util.ArrayList;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get parameters from the search form
        String orderIdParam = request.getParameter("orderId");
        Integer orderId = null;
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                orderId = null;
            }
        }

        String orderDate = request.getParameter("orderDate");
        if (orderDate != null && orderDate.isEmpty()) {
            orderDate = null;
        }

        List<Order> orders = new ArrayList<>();
        try {
            orders = orderDAO.searchOrders(user.getId(), orderId, orderDate);
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        }

        // Pass results and original search parameters back to JSP
        request.setAttribute("orders", orders);
        request.setAttribute("orderId", orderIdParam); 
        request.setAttribute("orderDate", orderDate);

        request.getRequestDispatcher("orderList.jsp").forward(request, response);
    }

}

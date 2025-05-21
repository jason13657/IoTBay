package controller;

import dao.OrderDAO;
import db.DBConnection;
import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        try {
            Connection conn = DBConnection.getConnection();
            orderDAO = new OrderDAO(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();

        // Optional filters
        String orderIdParam = req.getParameter("orderId");
        String orderDateParam = req.getParameter("orderDate");

        List<Order> orders;

        try {
            if ((orderIdParam != null && !orderIdParam.isEmpty()) ||
                (orderDateParam != null && !orderDateParam.isEmpty())) {
                orders = orderDAO.searchOrders(userId, orderIdParam, orderDateParam);
            } else {
                orders = orderDAO.getOrdersByUserId(userId);
            }

            req.setAttribute("orders", orders);
            req.getRequestDispatcher("Profiles.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("index.jsp?error=Could not load orders");
        }
    }
}

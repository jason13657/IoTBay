package controller;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order-list")
public class OrderListController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        List<Order> orders = orderDAO.getOrdersByCustomerId(customerId);
        request.setAttribute("orderList", orders);
        request.getRequestDispatcher("viewOrders.jsp").forward(request, response);
    }
}

package controller;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/order-delete")
public class OrderDeleteController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderDAO.getOrderById(orderId);

        if (order != null && order.getStatus().equals("saved")) {
            orderDAO.deleteOrder(orderId);
        }

        response.sendRedirect("order-list?customerId=" + order.getCustomerId());
    }
}

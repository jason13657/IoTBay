package controller;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("api/order-update")
public class OrderUpdateController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order existingOrder = orderDAO.getOrderById(orderId);
        request.setAttribute("order", existingOrder);
        request.getRequestDispatcher("editOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        Order order = orderDAO.getOrderById(orderId);
        if (order != null && order.getStatus().equals("saved")) {
            order.setTotalPrice(totalPrice);
            orderDAO.updateOrder(order);
        }

        response.sendRedirect("order-list?customerId=" + order.getCustomerId());
    }
}

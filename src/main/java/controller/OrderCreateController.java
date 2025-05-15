package controller;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/order-create")
public class OrderCreateController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("createOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setTotalPrice(totalPrice);
        order.setStatus("saved");

        orderDAO.createOrder(order);
        response.sendRedirect("order-list?customerId=" + customerId);
    }
}

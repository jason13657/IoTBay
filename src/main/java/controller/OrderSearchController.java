package controller;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("api/order-search")
public class OrderSearchController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String date = request.getParameter("date");
        List<Order> orders = orderDAO.searchOrders(keyword, date);
        request.setAttribute("orderList", orders);
        request.getRequestDispatcher("viewOrders.jsp").forward(request, response);
    }
}

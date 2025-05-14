package controller;

import dao.OrderDAO;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO(); // Instantiate DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "new":
                    showNewOrderForm(request, response);
                    break;
                case "edit":
                    showEditOrderForm(request, response);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                case "search":
                    searchOrders(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createOrder(request, response);
                    break;
                case "update":
                    updateOrder(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        // You would collect order items too in real code

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setTotalPrice(totalPrice);
        order.setStatus("saved");

        orderDAO.createOrder(order);
        response.sendRedirect("order?action=list");
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        Order order = orderDAO.getOrderById(orderId);
        if (order != null && order.getStatus().equals("saved")) {
            order.setTotalPrice(totalPrice);
            orderDAO.updateOrder(order);
        }

        response.sendRedirect("order?action=list");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderDAO.getOrderById(orderId);

        if (order != null && order.getStatus().equals("saved")) {
            orderDAO.deleteOrder(orderId);
        }

        response.sendRedirect("order?action=list");
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        List<Order> orders = orderDAO.getOrdersByCustomerId(customerId);
        request.setAttribute("orderList", orders);
        request.getRequestDispatcher("viewOrders.jsp").forward(request, response);
    }

    private void showNewOrderForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("createOrder.jsp").forward(request, response);
    }

    private void showEditOrderForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order existingOrder = orderDAO.getOrderById(orderId);
        request.setAttribute("order", existingOrder);
        request.getRequestDispatcher("editOrder.jsp").forward(request, response);
    }

    private void searchOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String date = request.getParameter("date"); // format yyyy-mm-dd
        List<Order> orders = orderDAO.searchOrders(keyword, date);
        request.setAttribute("orderList", orders);
        request.getRequestDispatcher("viewOrders.jsp").forward(request, response);
    }
}

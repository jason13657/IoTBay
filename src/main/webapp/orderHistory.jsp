<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    Integer userId = (user != null) ? user.getId() : null;
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Your Order History</h2>

    <form method="get" action="order-history">
        <label>Order ID: <input type="text" name="orderId" /></label>
        <label>Date: <input type="date" name="orderDate" /></label>
        <button type="submit">Search</button>
    </form>

    <hr>

    <%
        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
                if (order.getUserId() == userId) {
    %>
        <div>
            <p><strong>Order ID:</strong> <%= order.getOrderId() %></p>
            <p><strong>Date:</strong> <%= order.getOrderDate() %></p>
            <p><strong>Status:</strong> <%= order.getStatus() %></p>
            <p><strong>Total:</strong> $<%= order.getTotalAmount() %></p>
        </div>
        <hr>
    <%
                }
            }
        } else {
    %>
        <p>No orders found.</p>
    <%
        }
    %>
</body>
</html>

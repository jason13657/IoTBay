<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp"); // Redirect if not logged in
        return;
    }
    
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/styles.css"/>
        <title>Your Orders</title>
        <style>
            /* Styles only for the orders page */
            .orders-container {
                padding: 1em;
                width: 80%;
                margin: auto;
                font-family: Arial, sans-serif;
            }
            .orders-header h2 {
                border-bottom: 2px solid #0077b6;
                padding-bottom: 0.75em;
                color: #0077b6;
            }
            .orders-main {
                max-height: 50vh;
                overflow-y: auto;
            }
            .order-item {
                border-bottom: 1px solid #ddd;
                padding: 15px 10px;
                margin-bottom: 10px;
                background-color: #f9f9f9;
                border-radius: 8px;
            }
            .order-item p {
                margin: 5px 0;
                font-size: 16px;
            }
            .order-item strong {
                color: #0077b6;
            }
            .orders-nav {
                padding: 1em;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-bottom: 30px;
            }
            .orders-nav a img {
                max-width: 150px;
            }
            .orders-nav a:hover img {
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                cursor: pointer;
            }
            p.no-orders {
                font-size: 18px;
                color: #555;
                text-align: center;
                margin-top: 50px;
            }
        </style>
    </head>
    <body>
        <nav class="orders-nav">
            <a href="index.jsp"><img src="images/logo.png" alt="Logo"></a>
        </nav>
        <div class="orders-container">
            <section class="orders-section">
                <div class="orders-header">
                    <h2>Your Orders</h2>
                </div>
                <div class="orders-main">
                    <%
                        if (orders != null && !orders.isEmpty()) {
                            for (Order order : orders) {
                    %>
                        <div class="order-item">
                            <p><strong>Order ID:</strong> <%= order.getId() %></p>
                            <p><strong>Order Date:</strong> <%= order.getOrderDate() %></p>
                            <p><strong>Status:</strong> <%= order.getStatus() %></p>
                            <p><strong>Total Amount:</strong> $<%= String.format("%.2f", order.getTotalAmount()) %></p>
                        </div>
                    <%
                            }
                        } else {
                    %>
                        <p>You have no orders.</p>
                    <%
                        }
                    %>
                </div>
            </section>
        </div>
    </body>
</html>

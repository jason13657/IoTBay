<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    User user = (User) request.getAttribute("user");
    boolean isRegistered = (user != null);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
    <h2>Checkout</h2>

    <form action="CheckoutController" method="post">
        <% if (!isRegistered) { %>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        <% } else { %>
            <p>Name: <%= user.getName() %></p>
            <p>Email: <%= user.getEmail() %></p>
            <input type="hidden" name="name" value="<%= user.getName() %>">
            <input type="hidden" name="email" value="<%= user.getEmail() %>">
        <% } %>

        <label for="address">Shipping Address:</label>
        <textarea id="address" name="address" rows="4" required></textarea>

        <button type="submit">Submit Order</button>
    </form>
</body>
</html>

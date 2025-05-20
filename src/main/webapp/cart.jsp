<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    Integer userId;

    if (user != null) {
        userId = user.getId();
    } else {
        userId = (Integer) session.getAttribute("guestId");
    }

    // cartItems should already be set as a request attribute by a servlet
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/styles.css"/>
    <title>Your Cart</title>
</head>
<body>
    <nav class="cart-nav">
        <a href="index.jsp"><img src="images/logo.png" alt="Logo"></a>
    </nav>
    <div class="cart-container">
        <section class="cart-container">
            <div class="cart-header">
                <h2>Your Cart</h2>
            </div>
            <div class="cart-main">
                <%
                    if (cartItems != null && !cartItems.isEmpty()) {
                        for (CartItem item : cartItems) {
                %>
                    <div class="cart-item">
                        <p><strong>Product ID:</strong> <%= item.getProductId() %></p>
                        <p><strong>Quantity:</strong> <%= item.getQuantity() %></p>
                        <p><strong>Added At:</strong> <%= item.getAddedAt() %></p>
                    </div>
                <%
                        }
                    } else {
                %>
                    <p>Your cart is empty.</p>
                <%
                    }
                %>
            </div>
        
            <form action="CheckoutController" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Checkout</p>
                </button>
            </form>

            <form action="cart" method="post">
                <input type="hidden" name="action" value="clear" />
                <button type="submit" class="header__button">
                    <p class="header__button-text">Clear Cart</p>
                </button>
            </form>
        </section>
    </div>
</body>
</html>

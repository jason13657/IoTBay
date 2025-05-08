<%@ page import="model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>

<html>
<head>
    <title><%= product.getName() %></title>
    <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <section class="base__container">
        <div class="product__details">
            <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" width="500" height="300" />
            <h2><%= product.getName() %></h2>
            <p>Price: $<%= product.getPrice() %></p>
            <p>Description: <%= product.getDescription() %></p>

            <form method="post" action="wishlist">
                <input type="hidden" name="productId" value="<%= product.getId() %>" />
                <button type="submit">Add to Wishlist</button>
            </form>

            <form method="post" action="cart">
                <input type="hidden" name="productId" value="<%= product.getId() %>" />
                <button type="submit">Add to Cart</button>
            </form>
        </div>
    </section>
</body>
</html>

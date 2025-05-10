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
        <div class="product__container">
            <div class="product__left">
                <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" class="product__image" />
            </div>

            <div class="product__right">
                <h2 class="product__name"><%= product.getName() %></h2>
                <p class="product__price">$<%= product.getPrice() %></p>

                <form method="post" action="wishlist">
                    <input type="hidden" name="productId" value="<%= product.getId() %>" />
                    <button type="submit" class="product__btn product__btn--outline">Wishlist</button>
                </form>

                <form method="post" action="cart">
                    <input type="hidden" name="productId" value="<%= product.getId() %>" />
                    <button type="submit" class="product__btn">Add to Cart</button>
                </form>
            </div>
        </div>

        <div class="product__details-section">
            <h3 class="product__details-title">Product Details</h3>
            <p class="product__description"><%= product.getDescription() %></p>
        </div>
    </section>
</body>
</html>

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
        <div class="product__top">
            <div class="product__left">
                <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" class="product__image" onerror="this.onerror=null;this.src='https://i.imgur.com/EJLFNOwg.jpg';"/>
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
<style>
.product__description {
    color: #444;
}

.base__container {
    max-width: 900px;
    margin: 0 auto;        /* center horizontally */
    padding: 40px 50px;       /* spacing on sides for smaller screens */
    box-sizing: border-box;
}

    /* Layout for top section */
.product__top {
    display: flex;
    justify-content: space-between;
    gap: 40px;
    align-items: flex-start;
    margin-bottom: 40px;
}

/* Left image styling */
.product__left {
    flex: 1;
    max-width: 400px;
}

.product__image {
    width: 100%;
    height: auto;
    border-radius: 10px;
}

/* Right-side details */
.product__right {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.product__name {
    font-size: 28px;
    margin: 0;
}

.product__price {
    font-size: 22px;
    color: #444;
    margin: 0;
}

.product__details-section {
    margin-top: 30px;
}

.product__details-title {
    font-size: 24px;
    margin-bottom: 10px;
}

.product__description {
    font-size: 16px;
    line-height: 1.6;
}

    /* Basic button styles */
.product__btn {
    width: 200px;
    padding: 12px 24px;
    font-size: 16px;
    font-weight: bold;
    text-transform: uppercase;
    border: 2px solid #1F73B7; /* Darker blue */
    cursor: pointer;
    border-radius: 50px; /* Makes the buttons rounded with half-circle edges */
    transition: all 0.3s ease;
    margin: 0 100px;
}

/* "Add to Cart" button (Blue background with white text) */
.product__btn {
    background-color: #1F73B7; /* Darker blue */
    color: white;
}

/* "Wishlist" button (White background with blue text and blue outline) */
.product__btn--outline {
    background-color: white;
    color: #1F73B7; /* Darker blue */
}

/* Hover effects for both buttons */
.product__btn:hover, .product__btn--outline:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
}

/* Hover effects for "Wishlist" button */
.product__btn--outline:hover {
    background-color: #1F73B7;
    color: white;
}

/* Active state (button press effect) */
.product__btn:active, .product__btn--outline:active {
    transform: translateY(2px);
}

/* Focus styles */
.product__btn:focus, .product__btn--outline:focus {
    outline: none;
    border: 2px solid #1F73B7; /* Darker blue */
}

</style>
</html>

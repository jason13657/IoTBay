<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles.css"/>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />
        <div class="product__container">
            <h2>Results for "${keyword}"</h2>
            <ul>
            <c:forEach var="product" items="${results}">
                <li>${product.name} - $${product.price}</li>
            </c:forEach>
            </ul>
        </div>
    </body>
</html>

<!--                 <div class="product__card">
                    <img class="product__image" src="images/sample1.png" alt="Sample 1" />
                    <h4 class="product__title">Sensor</h4>
                    <p class="product__price">$459</p>
                </div> -->
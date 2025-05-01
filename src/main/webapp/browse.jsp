<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles.css"/>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />
        <div class="product__container">
            <c:forEach var="product" items="${results}">
                <p>${product.name}</p>
            </c:forEach>
        </div>
    </body>
</html>

<!--                 <div class="product__card">
                    <img class="product__image" src="images/sample1.png" alt="Sample 1" />
                    <h4 class="product__title">Sensor</h4>
                    <p class="product__price">$459</p>
                </div> -->
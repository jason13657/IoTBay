<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../css/styles.css" />
    <title>IoT Bay - Create Payment</title>
</head>
<body>
<jsp:include page="../components/header.jsp" />

<section class="register__container">
    <h2 class="register__title">Create Payment</h2>
    <div class="register__contents">
        <img class="register__image" src="../images/payment.png" alt="Payment" />
        <div class="register__form--container">
            <h3 class="register__form--title">Enter Payment Details</h3>
            <form class="register__form" action="<%=request.getContextPath()%>/payment/create" method="post" autocomplete="off">
                <input type="hidden" name="orderId" value="${orderId}" />
                <input type="text" name="paymentMethod" class="register__input" placeholder="Payment Method" required value="${param.paymentMethod}" />
                <input type="text" name="cardHolderName" class="register__input" placeholder="Card Holder Name" required value="${param.cardHolderName}" />
                <input type="text" name="cardNumber" class="register__input" placeholder="Card Number" required value="${param.cardNumber}" />
                <input type="text" name="cardExpiryDate" class="register__input" placeholder="Expiry Date (MM/YYYY)" required value="${param.cardExpiryDate}" />
                <input type="text" name="cardCVV" class="register__input" placeholder="CVV" required value="${param.cardCVV}" />
                <input type="number" step="0.01" name="amount" class="register__input" placeholder="Amount" required value="${param.amount}" />
                <button type="submit" class="register__button">Submit Payment</button>
            </form>
            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>
        </div>
    </div>
</section>

<jsp:include page="../components/footer.jsp" />
</body>
</html>

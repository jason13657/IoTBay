<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.Payment" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Payment Details</title>
</head>
<body>
<jsp:include page="../components/header.jsp" />

<section class="register__container">
    <h2 class="register__title">Payment Details</h2>
    <div class="register__contents">
        <div class="register__form--container">
            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty payment}">
                <table class="register__table">
                    <tr><th>Payment ID</th><td>${payment.paymentId}</td></tr>
                    <tr><th>Order ID</th><td>${payment.orderId}</td></tr>
                    <tr><th>Payment Method</th><td>${payment.paymentMethod}</td></tr>
                    <tr><th>Card Holder</th><td>${payment.cardHolderName}</td></tr>
                    <tr><th>Card Number</th><td>**** **** **** ${payment.cardNumber.substring(payment.cardNumber.length()-4)}</td></tr>
                    <tr><th>Expiry Date</th><td>${payment.cardExpiryDate}</td></tr>
                    <tr><th>Amount</th><td>${payment.amount}</td></tr>
                    <tr><th>Status</th><td>${payment.paymentStatus}</td></tr>
                    <tr><th>Date</th><td>${payment.paymentDate}</td></tr>
                </table>
                <a href="<%=request.getContextPath()%>/payment/edit?paymentId=${payment.paymentId}" class="register__button">Edit</a>
                <a href="<%=request.getContextPath()%>/payment/history" class="register__button">Back to History</a>
            </c:if>
        </div>
    </div>
</section>

<jsp:include page="../components/footer.jsp" />
</body>
</html>

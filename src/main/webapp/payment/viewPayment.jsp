<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.User" %>
<%@ page import="uts.isd.iotbay.model.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%-- TODO: Add any other necessary imports --%>

<html>
<head>
    <title>View Payment Details</title>
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> --%>
    <style>
        .payment-details { margin-bottom: 20px; }
        .payment-details p { margin: 5px 0; }
        .payment-details strong { display: inline-block; width: 150px; }
    </style>
</head>
<body>

    <%-- <jsp:include page="/components/header.jsp" /> --%>

    <h1>Payment Details</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>

    <%
        Payment payment = (Payment) request.getAttribute("payment");
        if (payment == null) {
    %>
        <p>Payment details not found or could not be loaded.</p>
        <p><a href="${pageContext.request.contextPath}/payment?action=history">Back to Payment History</a></p>
    <%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
            String maskedCardNumber = "N/A";
            if (payment.getCardNumber() != null && payment.getCardNumber().length() > 4) {
                maskedCardNumber = "**** **** **** " + payment.getCardNumber().substring(payment.getCardNumber().length() - 4);
            }
    %>
        <div class="payment-details">
            <p><strong>Payment ID:</strong> <%= payment.getPaymentId() %></p>
            <p><strong>Order ID:</strong> <a href="${pageContext.request.contextPath}/order?action=view&orderId=<%= payment.getOrderId() %>"><%= payment.getOrderId() %></a></p>
            <p><strong>Customer ID:</strong> <%= payment.getCustomerId() %></p>
            <p><strong>Payment Date:</strong> <%= payment.getPaymentDate() != null ? sdf.format(payment.getPaymentDate()) : "N/A" %></p>
            <p><strong>Amount:</strong> $<%= String.format("%.2f", payment.getAmount()) %></p>
            <p><strong>Payment Method:</strong> <%= payment.getPaymentMethod() != null ? payment.getPaymentMethod() : "N/A" %></p>
            <p><strong>Cardholder Name:</strong> <%= payment.getCardHolderName() != null ? payment.getCardHolderName() : "N/A" %></p>
            <p><strong>Card Number:</strong> <%= maskedCardNumber %></p> <%-- Display masked card number --%>
            <p><strong>Card Expiry Date:</strong> <%= payment.getCardExpiryDate() != null ? payment.getCardExpiryDate() : "N/A" %></p>
            <%-- CVV should NOT be displayed --%>
            <p><strong>Payment Status:</strong> <%= payment.getPaymentStatus() != null ? payment.getPaymentStatus() : "N/A" %></p>
        </div>

        <p>
            <a href="${pageContext.request.contextPath}/payment?action=history">Back to Payment History</a>
            <%
                // Conditional Edit button
                if ("SAVED".equalsIgnoreCase(payment.getPaymentStatus()) || "PENDING_SUBMISSION".equalsIgnoreCase(payment.getPaymentStatus())) {
            %>
                | <a href="${pageContext.request.contextPath}/payment?action=edit&paymentId=<%= payment.getPaymentId() %>">Edit Payment</a>
            <%
                }
            %>
        </p>
    <%
        }
    %>

    <%-- <jsp:include page="/components/footer.jsp" /> --%>

</body>
</html> 
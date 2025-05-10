<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.User" %> <%-- Assuming User bean for session --%>
<%@ page import="uts.isd.iotbay.model.Payment" %> <%-- For pre-filling form --%>
<%-- TODO: Add any other necessary imports --%>

<html>
<head>
    <title>Edit Payment Details</title>
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> --%>
</head>
<body>

    <%-- <jsp:include page="/components/header.jsp" /> --%>

    <h1>Edit Payment Details</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>
    <%-- TODO: Display specific field validation errors --%>

    <%
        Payment payment = (Payment) request.getAttribute("payment");
        // If payment is null, it means data wasn't passed correctly or an error occurred before loading.
        // You might want to redirect or show a more specific error.
        if (payment == null) {
            // Fallback to creating a new empty payment object to avoid NullPointerExceptions in the form
            // but ideally, the servlet should handle this and not forward if data is missing.
            payment = new Payment(); 
    %>
            <p style="color:red;">Could not load payment details to edit. Please try again.</p>
    <%
        }
    %>

    <form method="POST" action="${pageContext.request.contextPath}/payment">
        <input type="hidden" name="action" value="updatePayment">
        <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">
        <input type="hidden" name="orderId" value="<%= payment.getOrderId() %>"> <%-- Keep orderId for context --%>

        <div>
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="Credit Card" <%= "Credit Card".equals(payment.getPaymentMethod()) ? "selected" : "" %>>Credit Card</option>
                <%-- <option value="PayPal" <%= "PayPal".equals(payment.getPaymentMethod()) ? "selected" : "" %>>PayPal</option> --%>
            </select>
        </div>

        <div>
            <label for="cardHolderName">Cardholder Name:</label>
            <input type="text" id="cardHolderName" name="cardHolderName" value="<%= payment.getCardHolderName() != null ? payment.getCardHolderName() : "" %>" required>
        </div>

        <div>
            <label for="cardNumber">Card Number:</label>
            <%-- For security, consider not showing the full card number or only last 4 digits for display --%>
            <%-- If updating, user would re-enter. If displaying, mask it. --%>
            <%-- Value here is for pre-fill if policy allows, otherwise leave empty for re-entry --%>
            <input type="text" id="cardNumber" name="cardNumber" value="<%= payment.getCardNumber() != null ? payment.getCardNumber() : "" %>" required pattern="[0-9]{13,19}" title="13 to 19 digit card number">
        </div>

        <div>
            <label for="cardExpiryDate">Expiry Date (MM/YYYY):</label>
            <input type="text" id="cardExpiryDate" name="cardExpiryDate" value="<%= payment.getCardExpiryDate() != null ? payment.getCardExpiryDate() : "" %>" required pattern="(0[1-9]|1[0-2])\/[0-9]{4}" title="MM/YYYY">
        </div>

        <div>
            <label for="cardCVV">CVV (Re-enter if updating card details):</label>
            <input type="text" id="cardCVV" name="cardCVV" placeholder="Enter CVV if changing card" pattern="[0-9]{3,4}" title="3 or 4 digit CVV">
            <%-- CVV should generally not be pre-filled --%>
        </div>
        
        <div>
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" value="<%= payment.getAmount() %>" step="0.01" min="0.01" required readonly>
            <%-- Amount might be read-only during edit if tied to an order --%>
        </div>

        <div>
            <button type="submit">Update Payment Details</button>
            <a href="${pageContext.request.contextPath}/payment?action=history">Cancel</a>
        </div>
    </form>

    <%-- <jsp:include page="/components/footer.jsp" /> --%>

</body>
</html> 
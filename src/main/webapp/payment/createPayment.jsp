<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.User" %> <%-- Assuming User bean for session --%>
<%-- TODO: Add any other necessary imports like error message beans or Payment bean if pre-filling --%>

<html>
<head>
    <title>Create Payment Details</title>
    <%-- Link to your main CSS file or Bootstrap if used --%>
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> --%>
</head>
<body>

    <%-- TODO: Include a common header JSP if you have one --%>
    <%-- <jsp:include page="/components/header.jsp" /> --%>

    <h1>Add Payment Details</h1>

    <%-- Display general error messages from servlet --%>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>
    <%-- TODO: Display specific field validation errors if passed from servlet --%>

    <form method="POST" action="${pageContext.request.contextPath}/payment">
        <input type="hidden" name="action" value="createPayment">
        <%-- 
            Assuming orderId is passed as a request parameter or attribute 
            when navigating to this page. Or selected if user can choose order.
        --%>
        <% 
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null) orderIdStr = (String) request.getAttribute("orderId");
            if (orderIdStr == null) orderIdStr = ""; // Handle if not present, or make it a required field with validation
        %>
        <input type="hidden" name="orderId" value="<%= orderIdStr %>">

        <div>
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="Credit Card" selected>Credit Card</option>
                <%-- <option value="PayPal">PayPal</option> --%>
                <%-- Add other payment methods as needed --%>
            </select>
            <%-- TODO: Display validation error for paymentMethod --%>
        </div>

        <div>
            <label for="cardHolderName">Cardholder Name:</label>
            <input type="text" id="cardHolderName" name="cardHolderName" required>
            <%-- TODO: Display validation error for cardHolderName --%>
        </div>

        <div>
            <label for="cardNumber">Card Number:</label>
            <input type="text" id="cardNumber" name="cardNumber" required pattern="[0-9]{13,19}" title="13 to 19 digit card number">
            <%-- Note: Server-side validation is key. Pattern is for basic client-side UX hint. --%>
            <%-- TODO: Display validation error for cardNumber --%>
        </div>

        <div>
            <label for="cardExpiryDate">Expiry Date (MM/YYYY):</label>
            <input type="text" id="cardExpiryDate" name="cardExpiryDate" required pattern="(0[1-9]|1[0-2])\/[0-9]{4}" title="MM/YYYY">
            <%-- TODO: Display validation error for cardExpiryDate --%>
        </div>

        <div>
            <label for="cardCVV">CVV:</label>
            <input type="text" id="cardCVV" name="cardCVV" required pattern="[0-9]{3,4}" title="3 or 4 digit CVV">
            <%-- IMPORTANT: See notes on CVV storage in project.md and Payment.java --%>
            <%-- TODO: Display validation error for cardCVV --%>
        </div>
        
        <div>
            <label for="amount">Amount:</label>
            <%-- Amount might be pre-filled based on the order and potentially read-only --%>
            <% 
                String amountStr = request.getParameter("amount"); // Or from order details
                if (amountStr == null) amountStr = "0.00"; // Default or fetch from order
            %>
            <input type="number" id="amount" name="amount" value="<%= amountStr %>" step="0.01" min="0.01" required>
            <%-- TODO: Display validation error for amount --%>
        </div>

        <div>
            <button type="submit">Save Payment Details</button>
            <a href="${pageContext.request.contextPath}/order?action=view&orderId=<%= orderIdStr %>">Cancel</a> <%-- Adjust cancel link as needed --%>
        </div>
    </form>

    <%-- TODO: Include a common footer JSP if you have one --%>
    <%-- <jsp:include page="/components/footer.jsp" /> --%>

</body>
</html> 
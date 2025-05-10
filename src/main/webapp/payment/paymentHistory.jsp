<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.User" %>
<%@ page import="uts.isd.iotbay.model.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%-- TODO: Add any other necessary imports --%>

<html>
<head>
    <title>Payment History</title>
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> --%>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

    <%-- <jsp:include page="/components/header.jsp" /> --%>

    <h1>Your Payment History</h1>

    <%
        String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
        <p style="color:green;"><%= successMessage %></p>
    <%
        }
        String errorMessage = (String) request.getParameter("error"); // For simple errors passed via redirect
        if (errorMessage == null) errorMessage = (String) request.getAttribute("errorMessage"); // For errors from servlet
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>

    <%-- TODO: Add search/filter form here if implementing search functionality --%>
    <form method="GET" action="${pageContext.request.contextPath}/payment">
        <input type="hidden" name="action" value="history"> <%-- Or a specific search action --%>
        Search by Payment ID: <input type="text" name="paymentIdQuery" value="<%= request.getParameter("paymentIdQuery") != null ? request.getParameter("paymentIdQuery") : "" %>">
        Search by Date (YYYY-MM-DD): <input type="date" name="dateQuery" value="<%= request.getParameter("dateQuery") != null ? request.getParameter("dateQuery") : "" %>">
        <button type="submit">Search</button>
        <a href="${pageContext.request.contextPath}/payment?action=history">Clear Search</a>
    </form>
    <br>

    <%
        List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
        if (paymentList == null || paymentList.isEmpty()) {
    %>
        <p>You have no payment records.</p>
        <p><a href="${pageContext.request.contextPath}/payment?action=create&orderId=SOME_ORDER_ID">Add a new payment?</a></p> <%-- Link to create page might need order context --%>
    <%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
    %>
        <table>
            <thead>
                <tr>
                    <th>Payment ID</th>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Method</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Payment payment : paymentList) {
            %>
                <tr>
                    <td><%= payment.getPaymentId() %></td>
                    <td><a href="${pageContext.request.contextPath}/order?action=view&orderId=<%= payment.getOrderId() %>"><%= payment.getOrderId() %></a></td>
                    <td><%= payment.getPaymentDate() != null ? sdf.format(payment.getPaymentDate()) : "N/A" %></td>
                    <td>$<%= String.format("%.2f", payment.getAmount()) %></td>
                    <td><%= payment.getPaymentMethod() %></td>
                    <td><%= payment.getPaymentStatus() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/payment?action=view&paymentId=<%= payment.getPaymentId() %>">View</a>
                        <%
                            // Only allow edit/delete for certain statuses, e.g., "SAVED"
                            if ("SAVED".equalsIgnoreCase(payment.getPaymentStatus()) || "PENDING_SUBMISSION".equalsIgnoreCase(payment.getPaymentStatus())) {
                        %>
                            | <a href="${pageContext.request.contextPath}/payment?action=edit&paymentId=<%= payment.getPaymentId() %>">Edit</a>
                            | <a href="${pageContext.request.contextPath}/payment?action=deletePayment&paymentId=<%= payment.getPaymentId() %>" onclick="return confirm('Are you sure you want to delete this payment record?');">Delete</a>
                        <%
                            }
                        %>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <%
        }
    %>
    
    <%-- TODO: Add link to create a new payment if applicable (e.g., from an order page) --%>
    <%-- <p><a href="${pageContext.request.contextPath}/someOrderPage">Back to Orders</a></p> --%>

    <%-- <jsp:include page="/components/footer.jsp" /> --%>

</body>
</html> 
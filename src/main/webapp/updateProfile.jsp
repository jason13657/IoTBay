<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User user = (User) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Profile</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-container { max-width: 500px; margin: auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; }
        input[type=text], input[type=date], select {
            width: 100%; padding: 8px; box-sizing: border-box;
        }
        .error { color: #d32f2f; margin-bottom: 15px; }
        .success { color: #388e3c; margin-bottom: 15px; }
        button { padding: 10px 20px; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Profile</h2>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <% if (success != null) { %>
            <div class="success"><%= success %></div>
        <% } %>
        <form method="post" action="<%=request.getContextPath()%>/api/Profiles">
            <div class="form-group">
                <label>Email (cannot change)</label>
                <input type="text" value="<%= user != null ? user.getEmail() : "" %>" disabled />
            </div>
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input name="firstName" type="text" id="firstName" value="<%= user != null ? user.getFirstName() : "" %>" required />
            </div>
            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input name="lastName" type="text" id="lastName" value="<%= user != null ? user.getLastName() : "" %>" required />
            </div>
            <div class="form-group">
                <label for="phone">Phone</label>
                <input name="phone" type="text" id="phone" value="<%= user != null ? user.getPhone() : "" %>" required />
            </div>
            <div class="form-group">
                <label for="postalCode">Postal Code</label>
                <input name="postalCode" type="text" id="postalCode" value="<%= user != null ? user.getPostalCode() : "" %>" required />
            </div>
            <div class="form-group">
                <label for="addressLine1">Address Line 1</label>
                <input name="addressLine1" type="text" id="addressLine1" value="<%= user != null ? user.getAddressLine1() : "" %>" required />
            </div>
            <div class="form-group">
                <label for="addressLine2">Address Line 2</label>
                <input name="addressLine2" type="text" id="addressLine2" value="<%= user != null ? user.getAddressLine2() : "" %>" />
            </div>
            <div class="form-group">
                <label for="dateOfBirth">Date of Birth</label>
                <input name="dateOfBirth" type="date" id="dateOfBirth" value="<%= user != null && user.getDateOfBirth() != null ? user.getDateOfBirth() : "" %>" />
            </div>
            <div class="form-group">
                <label for="paymentMethod">Payment Method</label>
                <select name="paymentMethod" id="paymentMethod">
                    <option value="">Select</option>
                    <option value="CreditCard" <%= user != null && "CreditCard".equals(user.getPaymentMethod()) ? "selected" : "" %>>Credit Card</option>
                    <option value="PayPal" <%= user != null && "PayPal".equals(user.getPaymentMethod()) ? "selected" : "" %>>PayPal</option>
                    <option value="BankTransfer" <%= user != null && "BankTransfer".equals(user.getPaymentMethod()) ? "selected" : "" %>>Bank Transfer</option>
                </select>
            </div>
            <button type="submit">Update Profile</button>
        </form>
    </div>
</body>
</html>

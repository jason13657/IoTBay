<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="uts.isd.iotbay.model.Payment" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../css/styles.css" />
    <title>IoT Bay - Payment History</title>
</head>
<body>
<jsp:include page="../components/header.jsp" />

<section class="register__container">
    <h2 class="register__title">Payment History</h2>
    <div class="register__contents">
        <div class="register__form--container">
            <form class="register__form" method="get" action="<%=request.getContextPath()%>/payment/history">
                <input type="text" name="paymentIdQuery" class="register__input" placeholder="Search by Payment ID" value="${param.paymentIdQuery}" />
                <input type="date" name="dateQuery" class="register__input" value="${param.dateQuery}" />
                <button type="submit" class="register__button">Search</button>
                <a href="<%=request.getContextPath()%>/payment/create" class="register__button">Add New Payment</a>
            </form>
            <c:if test="${not empty successMessage}">
                <div class="success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>
            <table class="register__table">
                <thead>
                    <tr>
                        <th>Payment ID</th>
                        <th>Order ID</th>
                        <th>Method</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="payment" items="${paymentList}">
                    <tr>
                        <td>${payment.paymentId}</td>
                        <td>${payment.orderId}</td>
                        <td>${payment.paymentMethod}</td>
                        <td>${payment.amount}</td>
                        <td>${payment.paymentStatus}</td>
                        <td>${payment.paymentDate}</td>
                        <td>
                            <a href="<%=request.getContextPath()%>/payment/view?paymentId=${payment.paymentId}">View</a>
                            <a href="<%=request.getContextPath()%>/payment/edit?paymentId=${payment.paymentId}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</section>

<jsp:include page="../components/footer.jsp" />
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="../css/styles.css" />
    <title>IoT Bay - Create Payment</title>
    <script>
        // 간단한 실시간 입력 검증 예시 (카드 번호 숫자만 허용)
        function validateCardNumber(input) {
            input.value = input.value.replace(/\D/g, '');
        }
        // 카드 유효기간 MM/YYYY 포맷 체크 예시
        function validateExpiryDate(input) {
            const pattern = /^(0[1-9]|1[0-2])\/\d{4}$/;
            if (!pattern.test(input.value)) {
                input.setCustomValidity("Expiry date must be in MM/YYYY format");
            } else {
                input.setCustomValidity("");
            }
        }
    </script>
</head>
<body>
<jsp:include page="../components/header.jsp" />

<section class="register__container">
    <h2 class="register__title">Create Payment</h2>
    <div class="register__contents">
        <img class="register__image" src="../images/payment.png" alt="Payment" />
        <div class="register__form--container">
            <h3 class="register__form--title">Enter Payment Details</h3>
            <form class="register__form" action="${pageContext.request.contextPath}/payment/create" method="post" autocomplete="off" novalidate>
                <input type="hidden" name="orderId" value="${orderId}" />

                <label for="paymentMethod">Payment Method <span aria-hidden="true">*</span></label>
                <input type="text" id="paymentMethod" name="paymentMethod" class="register__input" placeholder="Payment Method" required
                       value="${fn:escapeXml(param.paymentMethod)}" />

                <label for="cardHolderName">Card Holder Name <span aria-hidden="true">*</span></label>
                <input type="text" id="cardHolderName" name="cardHolderName" class="register__input" placeholder="Card Holder Name" required
                       value="${fn:escapeXml(param.cardHolderName)}" />

                <label for="cardNumber">Card Number <span aria-hidden="true">*</span></label>
                <input type="text" id="cardNumber" name="cardNumber" class="register__input" placeholder="Card Number" required
                       maxlength="16" pattern="\d{13,16}" oninput="validateCardNumber(this)"
                       value="${fn:escapeXml(param.cardNumber)}" />

                <label for="cardExpiryDate">Expiry Date (MM/YYYY) <span aria-hidden="true">*</span></label>
                <input type="text" id="cardExpiryDate" name="cardExpiryDate" class="register__input" placeholder="MM/YYYY" required
                       pattern="(0[1-9]|1[0-2])\/\d{4}" oninput="validateExpiryDate(this)"
                       value="${fn:escapeXml(param.cardExpiryDate)}" />

                <label for="cardCVV">CVV <span aria-hidden="true">*</span></label>
                <input type="password" id="cardCVV" name="cardCVV" class="register__input" placeholder="CVV" required maxlength="4" pattern="\d{3,4}"
                       value="${fn:escapeXml(param.cardCVV)}" />

                <label for="amount">Amount <span aria-hidden="true">*</span></label>
                <input type="number" id="amount" name="amount" class="register__input" placeholder="Amount" required step="0.01" min="0.01"
                       value="${fn:escapeXml(param.amount)}" />

                <button type="submit" class="register__button">Submit Payment</button>
            </form>

            <c:if test="${not empty errorMessage}">
                <div class="error" role="alert">${errorMessage}</div>
            </c:if>
        </div>
    </div>
</section>

<jsp:include page="../components/footer.jsp" />
</body>
</html>

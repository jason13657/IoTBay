<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Secure Payment</title>
    <style>
        .paymentform__container {
            max-width: 420px;
            margin: 48px auto;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.09);
            padding: 32px 28px 24px 28px;
        }
        .paymentform__title {
            font-size: 26px;
            font-weight: 700;
            color: #232f3e;
            text-align: center;
            margin-bottom: 18px;
        }
        .paymentform__method-select {
            display: flex;
            gap: 12px;
            margin-bottom: 18px;
            justify-content: center;
        }
        .paymentform__method-btn {
            flex: 1;
            padding: 10px 0;
            background: #f3f4f6;
            border: 2px solid #d1d5db;
            border-radius: 10px;
            font-size: 17px;
            font-weight: 600;
            color: #232f3e;
            cursor: pointer;
            transition: border-color 0.2s, background 0.2s;
        }
        .paymentform__method-btn.selected,
        .paymentform__method-btn:focus {
            border-color: #ff9900;
            background: #fff7e6;
            outline: none;
        }
        .paymentform__form label {
            font-size: 15px;
            color: #232f3e;
            font-weight: 600;
            margin-bottom: 3px;
            display: block;
        }
        .paymentform__input {
            width: 100%;
            padding: 12px 14px;
            border-radius: 8px;
            border: 1.5px solid #b6bbc2;
            background: #f8fafc;
            font-size: 17px;
            margin-bottom: 12px;
            color: #232f3e;
            transition: border-color 0.2s;
        }
        .paymentform__input:focus {
            border-color: #ff9900;
            background: #fffbe6;
            outline: none;
        }
        .paymentform__pay-btn {
            width: 100%;
            background: linear-gradient(90deg, #ff9900 0%, #ffb84d 100%);
            color: #232f3e;
            border: none;
            border-radius: 10px;
            padding: 15px 0;
            font-size: 20px;
            font-weight: 700;
            margin-top: 10px;
            cursor: pointer;
            box-shadow: 0 2px 8px rgba(255,153,0,0.07);
            transition: background 0.18s, box-shadow 0.18s;
        }
        .paymentform__pay-btn:hover {
            background: linear-gradient(90deg, #ffb84d 0%, #ff9900 100%);
        }
        .paymentform__or {
            text-align: center;
            margin: 18px 0;
            color: #888;
            font-size: 15px;
            position: relative;
        }
        .paymentform__or:before,
        .paymentform__or:after {
            content: '';
            display: inline-block;
            width: 40%;
            height: 1px;
            background: #e0e0e0;
            vertical-align: middle;
            margin: 0 8px;
        }
        .paymentform__error {
            color: #e63946;
            background: #fff2f2;
            border-radius: 8px;
            padding: 10px;
            font-size: 15px;
            margin-bottom: 10px;
            text-align: center;
        }
        @media (max-width: 600px) {
            .paymentform__container {
                max-width: 98vw;
                padding: 16px 4px;
            }
        }
    </style>
    <script>
        function selectMethod(method) {
            document.getElementById('selectedMethod').value = method;
            document.getElementById('cardBtn').classList.remove('selected');
            document.getElementById('paypalBtn').classList.remove('selected');
            if (method === 'CARD') {
                document.getElementById('cardBtn').classList.add('selected');
                document.getElementById('cardFields').style.display = '';
                document.getElementById('paypalSection').style.display = 'none';
            } else {
                document.getElementById('paypalBtn').classList.add('selected');
                document.getElementById('cardFields').style.display = 'none';
                document.getElementById('paypalSection').style.display = '';
            }
        }
    </script>
</head>
<body>
<jsp:include page="../components/header.jsp" />

<section class="paymentform__container">
    <div class="paymentform__title">Secure Payment</div>
    <form class="paymentform__form" action="${pageContext.request.contextPath}/payment/create" method="post" autocomplete="off" novalidate>
        <input type="hidden" name="orderId" value="${orderId}" />
        <input type="hidden" id="selectedMethod" name="paymentMethod" value="CARD" />

        <div class="paymentform__method-select">
            <button type="button" id="cardBtn" class="paymentform__method-btn selected"
                onclick="selectMethod('CARD')" aria-pressed="true">Credit/Debit Card</button>
            <button type="button" id="paypalBtn" class="paymentform__method-btn"
                onclick="selectMethod('PAYPAL')" aria-pressed="false">
                <img src="../images/paypal-logo.png" alt="PayPal" style="height:20px;vertical-align:middle;margin-right:7px;" />
                PayPal
            </button>
        </div>

        <div id="cardFields">
            <label for="cardHolderName">Card Holder Name <span aria-hidden="true">*</span></label>
            <input type="text" id="cardHolderName" name="cardHolderName" class="paymentform__input" placeholder="Card Holder Name" required
                value="${fn:escapeXml(param.cardHolderName)}" />

            <label for="cardNumber">Card Number <span aria-hidden="true">*</span></label>
            <input type="text" id="cardNumber" name="cardNumber" class="paymentform__input" placeholder="Card Number" required
                maxlength="16" pattern="\d{13,16}" value="${fn:escapeXml(param.cardNumber)}" />

            <label for="cardExpiryDate">Expiry Date (MM/YYYY) <span aria-hidden="true">*</span></label>
            <input type="text" id="cardExpiryDate" name="cardExpiryDate" class="paymentform__input" placeholder="MM/YYYY" required
                pattern="(0[1-9]|1[0-2])\/\d{4}" value="${fn:escapeXml(param.cardExpiryDate)}" />

            <label for="cardCVV">CVV <span aria-hidden="true">*</span></label>
            <input type="password" id="cardCVV" name="cardCVV" class="paymentform__input" placeholder="CVV" required maxlength="4" pattern="\d{3,4}"
                value="${fn:escapeXml(param.cardCVV)}" />

            <label for="amount">Amount <span aria-hidden="true">*</span></label>
            <input type="number" id="amount" name="amount" class="paymentform__input" placeholder="Amount" required step="0.01" min="0.01"
                value="${fn:escapeXml(param.amount)}" />
        </div>

        <div id="paypalSection" style="display:none;">
            <div style="text-align:center;margin:22px 0;">
                <img src="../images/paypal-button.png" alt="Pay with PayPal" style="height:40px;"/>
                <p style="font-size:15px;color:#232f3e;margin-top:8px;">You will be redirected to PayPal to complete your payment securely.</p>
            </div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="paymentform__error" role="alert">${errorMessage}</div>
        </c:if>

        <button type="submit" class="paymentform__pay-btn">Pay Now</button>
    </form>
    <div class="paymentform__or">or</div>
    <a href="${pageContext.request.contextPath}/cart" style="display:block;text-align:center;font-size:15px;color:#0077b6;text-decoration:underline;">Back to Cart</a>
</section>

<jsp:include page="../components/footer.jsp" />
<script>
    selectMethod('CARD');
</script>
</body>
</html>

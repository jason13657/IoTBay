<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Register</title>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <section class="register__container">
        <h2 class="register__title">Join Us</h2>
        <div class="register__contents">
            <img class="register__image" src="images/wewantyou.png" alt="Register" />
            <div class="register__form--container">
                <h3 class="register__form--title">Create Account</h3>
                <form class="register__form" action="/api/register" method="post" autocomplete="off">
                    <!-- 이메일 -->
                     
                    <input type="email" id="email" name="email" class="register__input" placeholder="Email" required />

                    <!-- 비밀번호/확인 -->
                    <input type="password" id="password" name="password" class="register__input" placeholder="Password" required />
                    <input type="password" id="confirmPassword" name="confirmPassword" class="register__input" placeholder="Confirm Password" required />

                    <!-- 이름 -->
                    <input type="text" id="fullName" name="fullName" class="register__input" placeholder="Full Name" required />

                    <!-- 휴대폰 번호 -->
                    <input type="tel" id="phone" name="phone" class="register__input" placeholder="Phone Number" required />

                    <!-- 주소 -->
                    <input type="text" id="postalCode" name="postalCode" class="register__input" placeholder="Postal Code" required />
                    <input type="text" id="addressLine1" name="addressLine1" class="register__input" placeholder="Address Line 1" required />
                    <input type="text" id="addressLine2" name="addressLine2" class="register__input" placeholder="Address Line 2 (optional)" />

                    <!-- 생년월일 (선택) -->
                    <label for="dateOfBirth">Date of Birth (optional):</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" class="register__input" />

                    <!-- 결제수단 (선택) -->
                    <input type="text" id="paymentMethod" name="paymentMethod" class="register__input" placeholder="Preferred Payment Method (optional)" />

                    <!-- 약관동의 -->
                    <div class="tos__container">
                        <label for="tos">
                            <input type="checkbox" name="tos" id="tos" required/>
                            I agree to the Terms of Service
                        </label>
                    </div>

                    <button type="submit" class="register__button">Register</button>
                    <input type="hidden" name="from" value="register" />
                </form>
            </div>
        </div>
    </section>

    <jsp:include page="components/footer.jsp" />
</body>
</html>

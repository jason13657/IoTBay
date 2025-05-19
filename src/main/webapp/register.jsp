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
            <!-- <img class="register__image" src="images/wewantyou.png" alt="Register" /> -->
            <div class="register__form--container">

                <h3 class="register__form--title">Create Account</h3>
                <form class="register__form" action="/api/auth/register" method="post" autocomplete="off">
                     
                    <input type="email" id="email" name="email" class="register__input" placeholder="Email" required />

                    <input type="password" id="password" name="password" class="register__input" placeholder="Password" required />
                    <input type="password" id="confirmPassword" name="confirmPassword" class="register__input" placeholder="Confirm Password" required />


                    <input type="text" id="firstName" name="firstName" class="register__input" placeholder="First Name" required />
                    <input type="text" id="lastName" name="lastName" class="register__input" placeholder="Last Name" required />

                    
                    <input type="tel" id="phone" name="phone" class="register__input" placeholder="Phone Number" required />

                
                    <input type="text" id="postalCode" name="postalCode" class="register__input" placeholder="Postal Code" required />
                    <input type="text" id="addressLine1" name="addressLine1" class="register__input" placeholder="Address Line 1" required />
                    <input type="text" id="addressLine2" name="addressLine2" class="register__input" placeholder="Address Line 2 (optional)" />

                    <!-- 생년월일 (선택) -->
                    <label for="dateOfBirth">Date of Birth (optional):</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" class="register__input" />

          

                    <label for="paymentMethod">Payment Method</label>
                    <select name="paymentMethod" id="paymentMethod">
                        <option value="CreditCard">Credit Card</option>
                        <option value="PayPal">PayPal</option>
                        <option value="BankTransfer">Bank Transfer</option>
                    </select>

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


    <!-- error handling script -->
    <div id="errorModal" class="modal-overlay" style="display:none;">
    <div class="modal-content">
        <h2>Oops! Something went wrong.</h2>
        <p id="modalMessage"></p>
        <button onclick="closeModal()">Close</button>
    </div>
    </div>

    <jsp:include page="components/footer.jsp" />


     <!-- jQuery CDN (AJAX용) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    function showModal(message) {
        $('#modalMessage').text(message);
        $('#errorModal').fadeIn(150);
    }
    function closeModal() {
        $('#errorModal').fadeOut(150);
    }

    $('#registerForm').submit(function(e) {
        e.preventDefault();
        $.ajax({
            url: '/api/auth/register',
            method: 'POST',
            data: $(this).serialize(),
            success: function() {
                window.location.href = 'welcome.jsp';
            },
            error: function(xhr) {
                showModal(xhr.responseText); //s
            }
        });
    });
</script>
</body>
</html>

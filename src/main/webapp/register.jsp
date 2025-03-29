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
            <h2 class="register__title">Join Us</h3>

            <div class="register__contents">
                <img class="register__image" src="images/wewantyou.png" alt="Register" />
                <div class="register__form--container">
                    <h3 class="register__form--title">Create Account</h3>
                    <form  class="register__form" action="welcome.jsp" method="post" class="register__form">
                        <input type="email" id="email" name="email" class="register__input" placeholder="Email" required />
                        <input type="text" id="name" name="name" class="register__input" placeholder="User Name" required />
                        <input type="password" id="password" name="password" class="register__input" placeholder="Password" required />
                        <input type="Gender" id="gender" name="gender" class="register__input" placeholder="Gender" required />
                        <div class="favcol__container">
                            <label for="favcol">Favorite color</label>
                            <input type="color" id="favcol" class="register__input--favcol" name="favcol" />
                        </div>
                        <div class="tos__container">
                            <label for="tos">Terms of Service:</label>
                            <input type="checkbox" name="tos" id="tos" required/>
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
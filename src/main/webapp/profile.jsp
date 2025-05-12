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

        <section class="login__container">
            <h2 class="login__title">Log In</h2>
            <form class="login__form" method="post" action="api/login">
                <input type="email" id="email" name="email" class="register__input" placeholder="Email" required />
                <input type="password" id="password" name="password" class="register__input" placeholder="Password" required />
                <button type="submit" class="register__button">Log In</button>
                <input type="hidden" name="source" value="logins" />
            </form> 
        </section>

        <jsp:include page="components/footer.jsp" />
    </body>
</html>
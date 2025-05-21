<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css" />
    <title>IoT Bay - Register</title>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <section class="login__container">
        <h2 class="login__title">Log In</h2>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
        <div class="error-message" style="color:red; margin-bottom:10px;">
            <%= errorMessage %>
        </div>
        <% } %>
        <form class="login__form" method="post" action="<%=request.getContextPath()%>/api/login">
            <input type="email" id="email" name="email" class="register__input" placeholder="Email" required />
            <input type="password" id="password" name="password" class="register__input" placeholder="Password" required />
            <button type="submit" class="register__button">Log In</button>
            <input type="hidden" name="source" value="logins" />
        </form>
    </section>

    <jsp:include page="components/footer.jsp" />
</body>
</html>

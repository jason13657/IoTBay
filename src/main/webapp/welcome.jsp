<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    // Get the user object from the session (set by controller after registration/login)
    User user = (User) session.getAttribute("user");
    String from = (String) session.getAttribute("from");
    // Optional: Remove 'from' attribute after use to avoid confusion on refresh
    session.removeAttribute("from");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Welcome</title>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <section class="hero">
        <img class="hero__image" src="images/welcome.png" alt="IoT Hero Welcome" />   
    </section>

    <section class="welcome__container">
        <%
            if (user != null) {
                if ("register".equals(from)) {
        %>
                    <h2>Welcome, <%= user.getFullName() %>! <br> Your registration was successful.</h2>
                    <h3>Thank you for joining IoT Bay — your gateway to smarter living.</h3>
        <%
                } else {
        %>
                    <h2>Welcome back, <%= user.getFullName() %>!</h2>
                    <h3>You have successfully logged in to IoT Bay.<br>We are glad to see you again.</h3>
        <%
                }
            } else {
        %>
                <h2>Welcome!</h2>
                <h3>We're glad you're here.</h3>
        <%
            }
        %>
        <form action="index.jsp" method="post">
            <button class="header__button" type="submit">
                <p class="header__button-text">To home</p>
            </button>
        </form>
    </section>

    <jsp:include page="components/footer.jsp" />
</body>
</html>

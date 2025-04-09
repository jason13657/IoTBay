<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    String firstName = request.getParameter("firstName");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String lastName = request.getParameter("lastName");
    String gender = request.getParameter("gender");
    String favcol = request.getParameter("favcol");
    String dateOfBirth = request.getParameter("dateOfBirth");
    String createdAt = request.getParameter("createdAt");
    String updatedAt = request.getParameter("updatedAt");
    String role = request.getParameter("role");
    String from = request.getParameter("from");

    User user = new User(email, firstName, lastName, password, gender, favcol, dateOfBirth, createdAt, updatedAt, role);
    session.setAttribute("user", user);
%>

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
        <% if ("register".equals(from)) { %>
            <h2>Welcome, <%= user.getFullName() %>! <br> Your registration was successful.  </h2>
            <h3>Thank you for joining IoT Bay — your gateway to smarter living. </h3>
            <form action="index.jsp" method="post">
                <button class="header__button" type="submit">
                    <p class="header__button-text">To home</p>
                </button>
            </form>
        <% } else { %>
            <h2>Welcome back, <%= user.getName() %>! </h2>
            <h3>You have successfully logged in to IoT Bay.<br>We are glad to see you again. </h3>
            <form action="index.jsp" method="post">
                <button class="header__button" type="submit">
                    <p class="header__button-text">To home</p>
                </button>
            </form>
        <% } %>
        </section>

        

        <jsp:include page="components/footer.jsp" />
    </body>
</html>
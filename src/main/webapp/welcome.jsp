<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.iotbay.model.User" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String gender = request.getParameter("gender");
    String favcol = request.getParameter("favcol");
    LocalDateTime now = LocalDateTime.now();
    LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
    String from = request.getParameter("from");


    User user = new User(1, email, firstName, lastName, password, gender, favcol, dateOfBirth, now, now, "customer");
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
            <h2>Welcome back, <%= user.getFullName() %>! </h2>
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
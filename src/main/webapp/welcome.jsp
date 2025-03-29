<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String gender =  request.getParameter("gender");
    String favcol = request.getParameter("favcol");

    User user = new User(email, name, password, gender, favcol);
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
            <h2>Welcome, <%= name %>! <br> Your registration was successful.  </h2>
            <h3>Thank you for joining IoT Bay — your gateway to smarter living. </h3>
            <form action="index.jsp" method="post">
                <button class="header__button" type="submit">
                    <p class="header__button-text">To home</p>
                </button>
            </form>
        </section>

        

        <jsp:include page="components/footer.jsp" />
    </body>
</html>
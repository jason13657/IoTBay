<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<% 
    User user = (User)session.getAttribute("user"); 
%>


<header class="header">
    <a href="index.jsp">
        <img src="images/logo.png" alt="IoT Bay logo" />
    </a>
    <% if (user == null) { %>
    <div class="header__container">
        <form action="register.jsp" method="post">
            <button class="header__button" type="submit">
            <p class="header__button-text">Sign Up</p>
            </button>
        </form>
        <form action="login.jsp" method="post">
            <button class="header__button" type="submit">
            <p class="header__button-text">Log In</p>
            </button>
        </form>
    </div>
    <% } else { %>
        <form action="logout.jsp" method="post">
            <button class="header__button" type="submit">
            <p class="header__button-text">Log Out</p>
            </button>
        </form>
    <% } %>
</header>
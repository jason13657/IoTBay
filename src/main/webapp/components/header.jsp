<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<% 
    User user = (User)session.getAttribute("user"); 
%>


<header class="header">
    <div class="header__top">
        <a href="index.jsp">
            <img src="images/logo.png" alt="IoT Bay logo" />
        </a>
        <form action="search" method="get" id="searchForm">
            <input type="text" name="query" placeholder="Search Products..." id="searchInput" onkeyup="showSuggestions(this.value)" autocomplete="off" maxlength="35">
            <button type="submit">Search</button>
            <div id="suggestions" style="display:none; border: 1px solid #ccc; max-width: 200px; background-color: white; position: absolute; z-index: 10;"></div>
        </form>
    </div>
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
    <script>
    const path = window.location.pathname;
    const params = new URLSearchParams(window.location.search);

    if (
        path === '/index.jsp' ||
        path === '/' ||
        (path === '/search' && params.has('query'))
    ) {
        document.getElementById('searchForm').style.display = 'flex';
    } else {
        document.getElementById('searchForm').style.display = 'none';
    }

    document.getElementById("searchForm").addEventListener("submit", function(e) {
        const input = document.getElementById("searchInput");
        input.value = input.value.trim();
    });
    </script>
</header>
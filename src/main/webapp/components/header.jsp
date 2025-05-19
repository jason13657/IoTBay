<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<% 
    User user = (User)session.getAttribute("user"); 
    boolean isStaff = user != null && "staff".equalsIgnoreCase(user.getRole());
%>


<header class="header">
    <div class="header__top">
        <a href="index.jsp">
            <img src="images/logo.png" alt="IoT Bay logo" />
        </a>
        <form action="search" method="get" id="searchForm">
            <input 
                type="text" 
                name="query" 
                placeholder="Search Products..." 
                id="searchInput" 
                value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>"
                onkeyup="showSuggestions(this.value)" 
                autocomplete="off" 
                maxlength="35"
            >
        
            <select name="categoryId" id="categorySelect">
                <option value="">All Products</option>
                <option value="1" <%= "1".equals(request.getParameter("categoryId")) ? "selected" : "" %>>Sensors</option>
                <option value="2" <%= "2".equals(request.getParameter("categoryId")) ? "selected" : "" %>>Actuators</option>
                <option value="3" <%= "3".equals(request.getParameter("categoryId")) ? "selected" : "" %>>Gateways</option>
                <option value="4" <%= "4".equals(request.getParameter("categoryId")) ? "selected" : "" %>>Smart Devices</option>
                <option value="5" <%= "5".equals(request.getParameter("categoryId")) ? "selected" : "" %>>Security</option>
            </select>
        
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
        <div class="header__container">
            <form action="logout.jsp" method="post">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Log Out</p>
                </button>
            </form>

            <% if (isStaff) { %>
            <form action="/manage/products" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Products</p>
                </button>
            </form>
            <form action="/manage/users" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Users</p>
                </button>
            </form>
            <form action="/manage/access-logs" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Access Log</p>
                </button>
            </form>
            <% } %>
        </div>
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
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<% 
    User user = (User) session.getAttribute("user");
    out.println("user: " + user); // Debugging line
    boolean isStaff = user != null && "staff".equalsIgnoreCase(user.getRole());
%>

<header class="header">
    <div class="header__top" style="position: relative;">
        <a href="index.jsp">
            <img src="images/logo.png" alt="IoT Bay logo" />
        </a>
        <form action="search" method="get" id="searchForm" style="display: none;">
            <input type="text" name="query" placeholder="Search Products..." id="searchInput"
                   onkeyup="showSuggestions(this.value)" autocomplete="off" maxlength="35">
            <button type="submit">Search</button>
            <div id="suggestions"
                 style="display:none; border: 1px solid #ccc; background-color: white; position: absolute; top: 100%; left: 0; width: 100%; z-index: 1000;"></div>
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
            <form action="cart" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Cart</p>
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
            <form action="cart" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Cart</p>
                </button>
            </form>
            <form action="Profiles.jsp" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Profiles</p>
                </button>
            </form>

            <% if (isStaff) { %>
            <form action="/manage" method="get">
                <button class="header__button" type="submit">
                    <p class="header__button-text">Manage</p>
                </button>
            </form>
            <% } %>
        </div>
    <% } %>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const path = window.location.pathname;
            const params = new URLSearchParams(window.location.search);
            const searchForm = document.getElementById('searchForm');

            if (!searchForm) return;  // Safety check

            if (
                path.endsWith('index.jsp') ||
                path === '/' ||
                (path.endsWith('search') && params.has('query'))
            ) {
                searchForm.style.display = 'flex';
            } else {
                searchForm.style.display = 'none';
            }

            searchForm.addEventListener("submit", function (e) {
                const input = document.getElementById("searchInput");
                input.value = input.value.trim();
            });
        });
    </script>
</header>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/styles.css" />
        <title>IoT Bay - Dashboard</title>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />

        <section class="dashboard__container">
            <h2 class="dashboard__title">Welcome, ${user.fullName}!</h2>

            <div class="dashboard__options">
                <h3>Your Options</h3>
                <ul>
                    <li><a href="profile.jsp" class="dashboard__link">View & Update Profile</a></li>
                    <li><a href="accessLogs.jsp" class="dashboard__link">View Access Logs</a></li>
                    <li><a href="api/logout" class="dashboard__link">Log Out</a></li>
                </ul>
            </div>

            <div class="dashboard__logs">
                <h3>Recent Access Logs</h3>
                <c:if test="${not empty logs}">
                    <table class="logs__table">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Login Time</th>
                                <th>Logout Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="log" items="${logs}">
                                <tr>
                                    <td>${log.date}</td>
                                    <td>${log.loginTime}</td>
                                    <td>${log.logoutTime}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${empty logs}">
                    <p>You have no access logs yet.</p>
                </c:if>
            </div>
        </section>

        <jsp:include page="components/footer.jsp" />
    </body>
</html>

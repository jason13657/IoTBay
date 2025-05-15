<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Dashboard</title>
    <style>
        .dashboard__container {
    max-width: 1100px;
    margin: 48px auto 40px auto;
    background: #fff;
    border-radius: 22px;
    box-shadow: 0 8px 32px rgba(0,119,182,0.09), 0 2px 8px rgba(0,0,0,0.03);
    padding: 36px 32px 32px 32px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.dashboard__title {
    font-size: 38px;
    font-weight: 800;
    color: #0077b6;
    margin-bottom: 24px;
    letter-spacing: -1px;
    text-align: center;
}

.dashboard__options {
    margin-bottom: 36px;
    background: #f8fafc;
    border-radius: 14px;
    padding: 22px 28px;
    box-shadow: 0 1.5px 6px rgba(0,119,182,0.05);
}

.dashboard__options h3 {
    font-size: 22px;
    color: #0096c7;
    margin-bottom: 10px;
    font-weight: 700;
}

.dashboard__options ul {
    display: flex;
    gap: 22px;
    list-style: none;
    padding: 0;
    margin: 0;
}

.dashboard__link {
    display: inline-block;
    background: linear-gradient(90deg, #0077b6 0%, #00b4d8 100%);
    color: #fff;
    font-size: 17px;
    font-weight: 700;
    border-radius: 16px;
    padding: 12px 32px;
    text-decoration: none;
    transition: background 0.18s, box-shadow 0.18s;
    box-shadow: 0 2px 8px rgba(0,119,182,0.07);
}
.dashboard__link:hover {
    background: linear-gradient(90deg, #023e8a 0%, #0096c7 100%);
    color: #fff;
    text-decoration: underline;
}

.dashboard__logs {
    margin-top: 24px;
    width: 100%;
    max-width: 900px;
}

.dashboard__logs h3 {
    font-size: 22px;
    color: #0096c7;
    margin-bottom: 10px;
    font-weight: 700;
}

/* VRCX 스타일 카드형 테이블 */
.logs__table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0 14px;
    font-size: 17px;
    background: transparent;
    margin-top: 10px;
}

.logs__table th {
    background: #e0f7fa;
    color: #0077b6;
    font-weight: 700;
    border-radius: 12px 12px 0 0;
    font-size: 16px;
    padding: 14px 16px;
    text-align: left;
    border-bottom: 2px solid #bde0fe;
}

.logs__table tr {
    background: #fff;
    border-radius: 14px;
    box-shadow: 0 2px 12px rgba(0,119,182,0.09);
    transition: box-shadow 0.18s, transform 0.12s;
    border-left: 4px solid transparent;
}

.logs__table tr:hover {
    box-shadow: 0 6px 18px rgba(0,119,182,0.13);
    transform: translateY(-2px) scale(1.01);
    border-left: 4px solid #00b4d8;
}

.logs__table td {
    padding: 14px 16px;
    border-top: 1px solid #f1f5f9;
    border-bottom: 1px solid #f1f5f9;
    vertical-align: middle;
    font-size: 16px;
}

.logs__table tr:first-child td { border-top: none; }
.logs__table tr:last-child td { border-bottom: none; }

/* 오늘 로그 강조 */
.logs__table tr.log--today {
    background: #e0f7fa;
    border-left: 4px solid #0077b6;
}

/* 상태 컬럼 강조 */
.logs__table .status--success {
    color: #2ecc40;
    font-weight: 700;
}
.logs__table .status--fail {
    color: #e74c3c;
    font-weight: 700;
}

@media (max-width: 900px) {
    .dashboard__container {
        padding: 16px 2px;
    }
    .dashboard__options ul {
        flex-direction: column;
        gap: 12px;
    }
    .logs__table th, .logs__table td {
        padding: 8px 4px;
        font-size: 15px;
    }
    .dashboard__logs {
        max-width: 100%;
    }
}

    </style>
</head>
<body>
<jsp:include page="components/header.jsp" />

 <jsp:include page="components/header.jsp" />
        <nav class="nav__container">
            <ul class="nav__list">
                <li class="nav__item">
                    <a href="" class="nav__link">Industrial</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Warehouse</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Agriculture</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Smart Home</a>
                </li>
            </ul>
        </nav>
    <section class="hero">
            <img class="hero__image" src="images/hero.png" alt="IoT Hero main"/>
        </section>

<section class="dashboard__container">
    <h2 class="dashboard__title">Welcome, ${user.fullName}!</h2>

    <div class="dashboard__options">
        
        <ul>
            <li><a href="api/accessLogs.jsp" class="dashboard__link">View Access Logs</a></li>
            <li><a href="api/Profiles" class="dashboard__link">Return</a></li>
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
            <p style="color:#888;font-size:16px;margin-top:14px;">You have no access logs yet.</p>
        </c:if>
    </div>
</section>

<jsp:include page="components/footer.jsp" />
</body>
</html>

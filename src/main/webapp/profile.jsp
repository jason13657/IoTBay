<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/styles.css" />
    <title>IoT Bay - Profile</title>
    <style>
        .profile__container {
            max-width: 480px;
            margin: 48px auto 40px auto;
            background: #fff;
            border-radius: 22px;
            box-shadow: 0 8px 32px rgba(0,119,182,0.09), 0 2px 8px rgba(0,0,0,0.03);
            padding: 36px 32px 32px 32px;
        }
        .profile__title {
            font-size: 32px;
            font-weight: 800;
            color: #0077b6;
            margin-bottom: 10px;
            letter-spacing: -1px;
            text-align: center;
        }
        .profile__role {
            display: block;
            text-align: center;
            color: #0096c7;
            font-weight: 600;
            font-size: 16px;
            margin-bottom: 18px;
            letter-spacing: 0.5px;
        }
        .profile__form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        .profile__label {
            font-size: 15px;
            color: #222;
            font-weight: 600;
            margin-bottom: 5px;
            display: block;
        }
        .profile__input {
            width: 100%;
            padding: 13px 16px;
            border-radius: 12px;
            border: 1.5px solid #bde0fe;
            background: #f8fafc;
            font-size: 17px;
            color: #1a1a1a;
            box-shadow: 0 2px 8px rgba(0,119,182,0.04);
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .profile__input:focus {
            border-color: #0077b6;
            background: #e0f7fa;
            outline: none;
            box-shadow: 0 4px 16px rgba(0,119,182,0.12);
        }
        .profile__actions {
            display: flex;
            gap: 12px;
            margin-top: 8px;
        }
        .profile__button {
            flex: 1;
            background: linear-gradient(90deg, #0077b6 0%, #00b4d8 100%);
            color: #fff;
            border: none;
            border-radius: 12px;
            padding: 13px 0;
            font-size: 17px;
            font-weight: 700;
            cursor: pointer;
            box-shadow: 0 2px 8px rgba(0,119,182,0.07);
            transition: background 0.18s, box-shadow 0.18s;
        }
        .profile__button:hover {
            background: linear-gradient(90deg, #023e8a 0%, #0096c7 100%);
        }
        .profile__cancel {
            background: #e0e0e0;
            color: #0077b6;
            font-weight: 700;
            text-align: center;
            text-decoration: none;
        }
        .profile__cancel:hover {
            background: #bde0fe;
            color: #023e8a;
        }
        .profile__error {
            color: #e63946;
            background: #fff2f2;
            border-radius: 8px;
            padding: 10px;
            font-size: 15px;
            margin-bottom: 10px;
            text-align: center;
        }
        .profile__success {
            color: #38b000;
            background: #e8ffe8;
            border-radius: 8px;
            padding: 10px;
            font-size: 15px;
            margin-bottom: 10px;
            text-align: center;
        }
        .profile__meta {
            font-size: 13px;
            color: #888;
            margin-top: 8px;
            text-align: center;
        }
        @media (max-width: 600px) {
            .profile__container {
                max-width: 98vw;
                padding: 16px 4px;
            }
        }
    </style>
</head>
<body>
<jsp:include page="components/header.jsp" />

<section class="profile__container">
    <h2 class="profile__title">My Profile</h2>
    <span class="profile__role">
        <c:choose>
            <c:when test="${user.role eq 'staff'}">Staff</c:when>
            <c:when test="${user.role eq 'manager'}">Manager</c:when>
            <c:otherwise>Customer</c:otherwise>
        </c:choose>
    </span>
    <form class="profile__form" method="post" action="api/users/profile" autocomplete="off">
        <c:if test="${not empty errorMessage}">
            <div class="profile__error" role="alert">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="profile__success" role="alert">${successMessage}</div>
        </c:if>

        <label for="firstName" class="profile__label">First Name</label>
        <input type="text" id="firstName" name="firstName" class="profile__input" required
               value="${fn:escapeXml(user.firstName)}" placeholder="First Name" />

        <label for="lastName" class="profile__label">Last Name</label>
        <input type="text" id="lastName" name="lastName" class="profile__input" required
               value="${fn:escapeXml(user.lastName)}" placeholder="Last Name" />

        <label for="email" class="profile__label">Email (read-only)</label>
        <input type="email" id="email" name="email" class="profile__input" required
               value="${fn:escapeXml(user.email)}" placeholder="you@email.com" readonly />

        <c:if test="${user.role eq 'staff' || user.role eq 'manager'}">
            <label for="staffId" class="profile__label">Staff ID</label>
            <input type="text" id="staffId" name="staffId" class="profile__input"
                   value="${fn:escapeXml(user.staffId)}" placeholder="Staff ID" readonly />
        </c:if>

        <label for="phone" class="profile__label">Phone</label>
        <input type="text" id="phone" name="phone" class="profile__input"
               value="${fn:escapeXml(user.phone)}" placeholder="Phone Number" />

        <div class="profile__actions">
            <button type="submit" class="profile__button">Save Changes</button>
            <a href="dashboard.jsp" class="profile__button profile__cancel">Cancel</a>
        </div>
        <div class="profile__meta">
            Last updated: <c:out value="${user.updatedAt}" />
        </div>
    </form>
</section>

<jsp:include page="components/footer.jsp" />
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>My Profile - IoTBay</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div class="container">
    <h1>My Profile</h1>

    <!-- 메시지 및 에러 표시 -->
    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>

    <!-- 회원 정보 수정 폼 -->
    <form method="post" action="${pageContext.request.contextPath}/profile" class="form" autocomplete="on">
        <div class="form-group">
            <label for="email">Email (Username)</label>
            <input type="email" id="email" name="email" value="${user.email}" readonly class="form-control"/>
        </div>
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}" required class="form-control" autocomplete="given-name"/>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}" required class="form-control" autocomplete="family-name"/>
        </div>
        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="tel" id="phone" name="phone" value="${user.phone}" required class="form-control" autocomplete="tel"/>
        </div>

        <hr>
        <h3>Change Password</h3>
        <div class="form-group">
            <label for="currentPassword">Current Password</label>
            <input type="password" id="currentPassword" name="currentPassword" class="form-control" autocomplete="current-password"/>
        </div>
        <div class="form-group">
            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" class="form-control" autocomplete="new-password"/>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm New Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" autocomplete="new-password"/>
        </div>
        <button type="submit" class="btn btn-primary">Update Profile</button>
    </form>

    <hr>

    <!-- 회원 탈퇴 폼 -->
    <form method="post" action="${pageContext.request.contextPath}/profile"
          onsubmit="return confirm('정말 회원 탈퇴하시겠습니까? 탈퇴 시 모든 주문이 취소됩니다.');">
        <input type="hidden" name="action" value="cancel"/>
        <button type="submit" class="btn btn-danger">회원 탈퇴</button>
    </form>

    <div style="margin-top:20px;">
        <a href="${pageContext.request.contextPath}/" class="btn btn-link">Back to Home</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login History - IoTBay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    
    <div class="container">
        <h1>My Login History</h1>
        
        <%-- 에러 메시지 출력 --%>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>
        
        <%-- 날짜 검색 폼 --%>
        <form action="${pageContext.request.contextPath}/accessLogs" method="get" class="search-form">
            <div class="form-group">
                <label for="searchDate">Search by Date</label>
                <input type="date" id="searchDate" name="searchDate" value="${searchDate}">
                <button type="submit" class="btn secondary">Search</button>
                <a href="${pageContext.request.contextPath}/accessLogs" class="btn link">View All</a>
            </div>
        </form>
        
        <%-- 로그인 기록 테이블 --%>
        <div class="table-container">
            <c:choose>
                <c:when test="${empty logs}">
                    <p>No login history found.</p>
                </c:when>
                <c:otherwise>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Login Time</th>
                                <th>Logout Time</th>
                                <th>IP Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="log" items="${logs}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td><fmt:formatDate value="${log.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty log.logoutTime}">
                                                <span class="badge badge-info">Ongoing</span>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${log.logoutTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${log.ipAddress}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/profile" class="btn secondary">Back to Profile</a>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>

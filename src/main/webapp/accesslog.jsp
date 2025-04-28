<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인 기록 - IoTBay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    
    <div class="container">
        <h1>내 로그인 기록</h1>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/accessLogs" method="get" class="search-form">
            <div class="form-group">
                <label for="searchDate">날짜로 검색</label>
                <input type="date" id="searchDate" name="searchDate" value="${searchDate}">
                <button type="submit" class="btn secondary">검색</button>
                <a href="${pageContext.request.contextPath}/accessLogs" class="btn link">전체 보기</a>
            </div>
        </form>
        
        <div class="table-container">
            <c:choose>
                <c:when test="${empty logs}">
                    <p>로그인 기록이 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>로그인 시간</th>
                                <th>로그아웃 시간</th>
                                <th>IP 주소</th>
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
                                                <span class="badge badge-info">진행 중</span>
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
            <a href="${pageContext.request.contextPath}/profile" class="btn secondary">내 정보로 돌아가기</a>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>

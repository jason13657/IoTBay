<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>계정 삭제 - IoTBay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    
    <div class="container">
        <h1>계정 삭제</h1>
        
        <div class="alert alert-warning">
            <strong>주의!</strong> 계정을 삭제하면 복구할 수 없습니다. 정말 삭제하시겠습니까?
        </div>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/deleteAccount" method="post">
            <div class="form-group">
                <label for="password">비밀번호 확인</label>
                <input type="password" id="password" name="password" required>
                <small>보안을 위해 비밀번호를 입력해주세요.</small>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn danger">계정 삭제</button>
                <a href="${pageContext.request.contextPath}/profile" class="btn secondary">취소</a>
            </div>
        </form>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>

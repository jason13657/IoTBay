<%@ page import="javax.servlet.http.*, java.time.LocalDateTime" %>
<%@ page import="dao.AccessLogDAOImpl, dao.interfaces.AccessLogDAO, db.DBConnection, model.AccessLog, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        try {
            java.sql.Connection conn = db.DBConnection.getConnection();
            AccessLogDAO accessLogDAO = new AccessLogDAOImpl(conn);
            AccessLog log = new AccessLog(0, user.getId(), "User " + user.getEmail() + " Logged out", LocalDateTime.now());
            accessLogDAO.createAccessLog(log);
            conn.close();
        } catch (Exception e) {
            // 로그 기록 실패시 무시 (또는 로그 남기기)
            e.printStackTrace();
        }
    }
    session.invalidate();
    response.sendRedirect("index.jsp");
%>

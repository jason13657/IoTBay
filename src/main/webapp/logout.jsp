<%@ page import="javax.servlet.http.*, java.time.LocalDateTime" %>
<%@ page import="dao.AccessLogDAOImpl, dao.interfaces.AccessLogDAO, db.DBConnection, model.AccessLog, model.User" %>
<%
    User user = (User) session.getAttribute("user");

    if (user != null) {
        try {
            java.sql.Connection conn = db.DBConnection.getConnection();
            AccessLogDAO accessLogDAO = new AccessLogDAOImpl(conn);
            AccessLog log = new AccessLog(
                0,
                user.getId(),
                "User " + user.getEmail() + " Logged out",
                LocalDateTime.now()
            );
            accessLogDAO.createAccessLog(log);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    session.invalidate();
    response.sendRedirect(request.getContextPath() + "/index.jsp");
%>

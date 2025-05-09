package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccessLogDAOImpl;
import dao.interfaces.AccessLogDAO;
import db.DBConnection;
import model.AccessLog;

@WebServlet("/api/logout")
public class LogOutController extends HttpServlet {

    private AccessLogDAO accessLogDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize AccessLogDAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing userId parameter\"}");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);

            // 로그 생성자 수정: logId를 0 또는 적절한 기본값으로 설정
            AccessLog accessLog = new AccessLog(0, user.getId(), "Logout successful", LocalDateTime.now());

            accessLogDAO.addAccessLog(accessLog); // 또는 addAccessLog → 실제 메서드명 확인 필요
            //it still has a issue with wmethod name, it needs to be fixed 

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Logout successful\"}");

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid userId format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Server error: " + e.getMessage() + "\"}");
        }
    }
}

//logout, it removes the session
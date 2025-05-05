package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dao.AccessLogDAOImpl;
import model.AccessLog;
import model.User;

@WebServlet("/accessLogs")
public class AccessLogServlet extends HttpServlet {
    private AccessLogDAOImpl accessLogDAO;
    private Connection connection;

    @Override
    public void init() {
        try {
            // 1. DataSource를 통해 Connection 획득
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/yourDB");
            connection = ds.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (Exception e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userId = ((User) session.getAttribute("user")).getId();
        String searchDateStr = req.getParameter("searchDate");

        try {
            List<AccessLog> logs;
            if (searchDateStr != null && !searchDateStr.trim().isEmpty()) {
                // 2. java.util.Date → java.sql.Date 변환
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateStr);
                Date sqlDate = new Date(utilDate.getTime()); // 변환
                logs = accessLogDAO.findByUserIdAndDate(userId, sqlDate); // 수정된 메서드 호출
                req.setAttribute("searchDate", searchDateStr);
            } else {
                // 3. DAO 메서드명 일치화 (findByUserId → getAccessLogsByUserId)
                logs = accessLogDAO.getAccessLogsByUserId(userId);
            }

            req.setAttribute("logs", logs);
            req.getRequestDispatcher("/WEB-INF/views/accessLogs.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "오류: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/accessLogs.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
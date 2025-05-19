package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccessLogDAOImpl;
import dao.interfaces.AccessLogDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

@WebServlet("/api/accessLog")
public class AccesslogController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private static final Logger logger = Logger.getLogger(AccesslogController.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            this.accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed in AccesslogController.init()", e);
            throw new ServletException("Database connection failed.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 세션 및 로그인 체크
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // 2. 날짜 검색 파라미터 처리
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        LocalDate startDate = null, endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr);
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "날짜 형식이 올바르지 않습니다.");
        }

        // 3. DB에서 본인 로그만 조회
        List<AccessLog> accessLogList = null;
        try {
            if (startDate != null && endDate != null) {
                accessLogList = accessLogDAO.getAccessLogsByUserIdAndDateRange(user.getId(), startDate, endDate);
            } else {
                accessLogList = accessLogDAO.getAccessLogsByUserId(user.getId());
            }
            request.setAttribute("accessLogList", accessLogList);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve access logs for userId " + user.getId(), e);
            request.setAttribute("error", "접속 기록 조회 중 오류가 발생했습니다.");
        }

        // 4. JSP로 포워딩 (수정/삭제 기능 없음)
        request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
    }

    // POST, PUT, DELETE 등은 구현하지 않음 (접속 로그는 사용자 직접 수정/삭제 불가)
}

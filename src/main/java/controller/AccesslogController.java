package controller;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("api/accessLog")
public class AccesslogController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = DBConnection.getConnection();
        this.accessLogDAO = new AccessLogDAOImpl(connection);
        this.userDAO = new UserDAOImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 로그인된 사용자 세션에서 userId 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");

        // 날짜 검색 파라미터 처리
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        List<AccessLog> accessLogList;

        try {
            if (startDateStr != null && endDateStr != null &&
                !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                accessLogList = accessLogDAO.getAccessLogsByUserIdAndDateRange(user.getId(), startDate, endDate);
            } else {
                accessLogList = accessLogDAO.getAccessLogsByUserId(user.getId());
            }
            request.setAttribute("accessLogList", accessLogList);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "접근 로그를 불러오는 중 오류가 발생했습니다.");
        }

        // JSP로 포워딩
        request.getRequestDispatcher("accessLog.jsp").forward(request, response);
    }
}

package controller;

import dao.AccessLogDAOImpl;
import model.AccessLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/accessLogs")
public class AccessLogServlet extends HttpServlet {
    
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() {
        accessLogDAO = new AccessLogDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userId = ((model.User) session.getAttribute("user")).getId();
        String searchDateStr = req.getParameter("searchDate");

        try {
            List<AccessLog> logs;
            if (searchDateStr != null && !searchDateStr.trim().isEmpty()) {
                Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateStr);
                logs = accessLogDAO.findByUserIdAndDate(userId, searchDate);
                req.setAttribute("searchDate", searchDateStr);
            } else {
                logs = accessLogDAO.findByUserId(userId);
            }

            req.setAttribute("logs", logs);
            req.getRequestDispatcher("/WEB-INF/views/accessLogs.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "접속 기록 조회 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/accessLogs.jsp").forward(req, resp);
        }
    }
}

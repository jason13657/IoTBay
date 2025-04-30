package controller;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import model.AccessLog;
import model.User;
import util.DBUtil;
import util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/login")
public class LoginServletController extends HttpServlet {
    
    private static final Logger logger = LogManager.getLogger(LoginServletController.class);
    private UserDAOImpl userDAO;
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBUtil.getConnection();
            userDAO = new UserDAOImpl(conn);
            accessLogDAO = new AccessLogDAOImpl(conn);
        } catch (SQLException e) {
            logger.error("DAO 초기화 실패", e);
            throw new ServletException("서버 초기화 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession existingSession = req.getSession(false);
        if (existingSession != null && existingSession.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password");

        try {
            User user = userDAO.findByEmail(email);
            if (user == null || !PasswordUtil.verifyPassword(password, user.getPassword())) {
                req.setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userType", user.getUserType());
            session.setMaxInactiveInterval(30 * 60); // 30분 세션 유지

            // 접속 로그 기록
            AccessLog log = new AccessLog();
            log.setUserId(user.getId());
            log.setLoginTime(new Timestamp(System.currentTimeMillis()));
            log.setIpAddress(req.getRemoteAddr());

            int logId = accessLogDAO.createLoginLog(log);
            session.setAttribute("currentLogId", logId);

            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } catch (SQLException e) {
            logger.error("로그인 처리 중 데이터베이스 오류", e);
            req.setAttribute("error", "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error("로그인 처리 중 예상치 못한 오류", e);
            req.setAttribute("error", "알 수 없는 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
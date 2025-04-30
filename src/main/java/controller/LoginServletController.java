package controller;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import model.AccessLog;
import model.User;
import utils.PasswordUtil;
import utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

@WebServlet("/login")
public class LoginServletController extends HttpServlet {

    private UserDAOImpl userDAO;
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() {
        // DB 커넥션 생성 (DBUtil.getConnection()은 예시입니다. 실제 환경에 맞게 수정하세요)
        Connection conn = DBUtil.getConnection();
        userDAO = new UserDAOImpl(conn);
        accessLogDAO = new AccessLogDAOImpl(conn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) != null && req.getSession(false).getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            // 이메일로 사용자 찾기
            User user = userDAO.findByEmail(email);
            if (user == null || !PasswordUtil.verifyPassword(password, user.getPassword())) {
                req.setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userType", user.getRole());

            // 로그인 기록 생성
            AccessLog log = new AccessLog();
            log.setUserId(user.getId());
            log.setLoginTime(new Timestamp(System.currentTimeMillis()));
            log.setLogoutTime(null);
            log.setIpAddress(req.getRemoteAddr());

            int logId = accessLogDAO.createLoginLog(log);
            session.setAttribute("currentLogId", logId);

            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "로그인 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}

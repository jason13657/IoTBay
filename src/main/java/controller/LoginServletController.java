package controller;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import model.AccessLog;
import model.User;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/login")
public class LoginServletController extends HttpServlet {
    
    private UserDAOImpl userDAO;
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl();
        accessLogDAO = new AccessLogDAOImpl();
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
            User user = userDAO.findByEmail(email);
            if (user == null || !PasswordUtil.verifyPassword(password, user.getPassword())) {
                req.setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userType", user.getUserType());

            // 로그인 기록 생성
            AccessLog log = new AccessLog();
            log.setUserId(user.getId());
            log.setLoginTime(new Date());
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

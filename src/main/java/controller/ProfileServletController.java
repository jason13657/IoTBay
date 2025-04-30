package controller;

import dao.UserDAOImpl;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServletController extends HttpServlet {
    
    private UserDAOImpl userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        String error = ValidationUtil.validateProfileUpdate(fullName, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        try {
            user.setFullName(fullName);
            user.setPhone(phone);

            if (currentPassword != null && !currentPassword.isEmpty()) {
                if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
                    req.setAttribute("error", "현재 비밀번호가 올바르지 않습니다.");
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                    return;
                }

                error = ValidationUtil.validatePasswordChange(newPassword, confirmPassword);
                if (error != null) {
                    req.setAttribute("error", error);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                    return;
                }

                user.setPassword(PasswordUtil.hashPassword(newPassword));
            }

            userDAO.update(user);
            session.setAttribute("user", user);

            req.setAttribute("message", "정보가 성공적으로 업데이트되었습니다.");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "정보 업데이트 중 오류가 발생했습니다.");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        }
    }
}

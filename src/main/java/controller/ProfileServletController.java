package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAOImpl;
import db.DBConnection;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

@WebServlet("/profile")
public class ProfileServletController extends HttpServlet {

    private UserDAOImpl userDAO;

    @Override
    public void init() {
        try {
            Connection conn = DBConnection.getConnection();
            userDAO = new UserDAOImpl(conn);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // 필요하다면: throw new RuntimeException(e);
        }
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

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // 유효성 검사
        String error = ValidationUtil.validateProfileUpdate(firstName, lastName, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        try {
            user.setFirstName(firstName);
            user.setLastName(lastName);
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

            userDAO.updateUser(user.getId(), user);
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

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAOImpl;
import db.DBConnection;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

@WebServlet("/register")
public class RegisterServletController extends HttpServlet {

    private UserDAOImpl userDAO;

    @Override
    public void init() {
        try {
            Connection conn = DBConnection.getConnection();
            userDAO = new UserDAOImpl(conn);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String phone = req.getParameter("phone");
        String userType = req.getParameter("userType");

        // 서버측 검증 (userType은 검증 메서드에 넘기지 않음)
        String error = ValidationUtil.validateRegistration(fullName, email, password, confirmPassword, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            // 메서드명은 실제 UserDAOImpl에 맞게 수정 (예: findByEmail)
            if (userDAO.getUserByEmail(email) != null) {
                req.setAttribute("error", "이미 존재하는 이메일입니다.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setPhone(phone);
            user.setRole(userType);

            userDAO.createUser(user);

            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "회원가입 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}

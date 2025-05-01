package controller;

import dao.UserDAOImpl;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;
import db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
            // 필요시 throw new ServletException(e);
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

        // 서버측 검증 (ValidationUtil 메서드 시그니처에 맞게 수정)
        String error = ValidationUtil.validateRegistration(fullName, email, password, confirmPassword, phone, userType);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            // findByEmail 또는 getUserByEmail 등 실제 메서드명에 맞게 수정
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

            // createUser로 메서드명 변경 (UserDAOImpl에 맞게)
            userDAO.createUser(user);

            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "회원가입 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}

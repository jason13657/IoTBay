package controller;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;
import utils.ValidationUtil;
import utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");

        // 주소 정보
        String postalCode = request.getParameter("postalCode");
        String addressLine1 = request.getParameter("addressLine1");
        String addressLine2 = request.getParameter("addressLine2");

        // 선택 정보
        String dobString = request.getParameter("dateOfBirth");
        String paymentMethod = request.getParameter("paymentMethod");

        // 1. 필수 정보 유효성 검사
        String profileError = ValidationUtil.validateOrderUserProfile(fullName, phone, postalCode, addressLine1);
        if (profileError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, profileError);
            return;
        }

        // 2. 이메일 유효성 검사
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, emailError);
            return;
        }

        // 3. 비밀번호 및 확인 비밀번호 유효성 검사
        String passwordError = ValidationUtil.validatePasswordChange(password, confirmPassword);
        if (passwordError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, passwordError);
            return;
        }

        // 4. 중복 이메일 검사
        try {
            if (userDAO.getUserByEmail(email) != null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email already exists.");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            return;
        }

        // 5. 생년월일 파싱 (선택)
        LocalDate dateOfBirth = null;
        if (dobString != null && !dobString.trim().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dobString);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date of birth.");
                return;
            }
        }

        // 6. 비밀번호 해싱
        String hashedPassword = PasswordUtil.hashPassword(password);

        LocalDateTime now = LocalDateTime.now();

        try {
            // id는 auto-increment이므로 0 또는 null로 설정
            User newUser = new User(
                0, // id
                email,
                hashedPassword,
                fullName,
                phone,
                postalCode,
                addressLine1,
                addressLine2,
                dateOfBirth,
                paymentMethod,
                now,
                now,
                "user",
                true
            );

            userDAO.createUser(newUser);

            // 성공 시
            response.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

@WebServlet("/api/auth/register")
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

        // 1. 입력값 받기
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String postalCode = request.getParameter("postalCode");
        String addressLine1 = request.getParameter("addressLine1");
        String addressLine2 = request.getParameter("addressLine2");
        String dobString = request.getParameter("dateOfBirth");
        String paymentMethod = request.getParameter("paymentMethod");

        // 2. 필수 정보 유효성 검사
        String profileError = ValidationUtil.validateRegisterUserProfile(
                firstName, lastName, phone, postalCode, addressLine1
        );
        if (profileError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, profileError);
            return;
        }

        // 3. 이메일 유효성 검사
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, emailError);
            return;
        }

        // 4. 비밀번호 및 확인 비밀번호 유효성 검사
        String passwordError = ValidationUtil.validatePasswordChange(password, confirmPassword);
        if (passwordError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, passwordError);
            return;
        }

        // 5. 중복 이메일 검사
        try {
            if (userDAO.getUserByEmail(email) != null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email already exists.");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            return;
        }

        // 6. 생년월일 파싱 (선택)
        LocalDate dateOfBirth = null;
        if (dobString != null && !dobString.trim().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dobString);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date of birth.");
                return;
            }
        }

        // 7. 비밀번호 해싱
        String hashedPassword = PasswordUtil.hashPassword(password);

        LocalDateTime now = LocalDateTime.now();

        try {
            // User 객체 생성 (필드 순서 및 구조는 User 모델에 맞게)
            User newUser = new User(
                0, // id (auto-increment)
                email,
                hashedPassword,
                firstName,
                lastName,
                phone,
                postalCode,
                addressLine1,
                addressLine2,
                dateOfBirth,
                paymentMethod,
                now,
                now,
                "user", // 기본 role
                true    // 활성화 여부
            );

            userDAO.createUser(newUser);

            // 회원가입 성공 시
            // 세션에 사용자 정보 저장 등 추가 가능
            response.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

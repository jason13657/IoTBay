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

        String tos = request.getParameter("tos");
        if (tos == null || !tos.equals("on")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You must accept the terms of service.");
            return;
        }

        String profileError = ValidationUtil.validateRegisterUserProfile(
                firstName, lastName, phone, postalCode, addressLine1
        );

        
        if (profileError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, profileError);
            return;     
        }

        // 3. validate email
        String emailError = ValidationUtil.validateEmail(email);
        if (emailError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, emailError);
            return;
        }

        // 4.confirm the password
        String passwordError = ValidationUtil.validatePasswordChange(password, confirmPassword);
        if (passwordError != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, passwordError);
            return;
        }

        // 5. validate email duplication
        try {
            if (userDAO.getUserByEmail(email) != null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email already exists.");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            return;
        }

        // 6. date of birth / optional
        LocalDate dateOfBirth = null;
        if (dobString != null && !dobString.trim().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dobString);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date of birth.");
                return;
            }
        }

        // parse the password
        String hashedPassword = PasswordUtil.hashPassword(password);

        LocalDateTime now = LocalDateTime.now();

        try {
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
                "customer", // default role
                true    
            );

            userDAO.createUser(newUser);

          
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");


        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

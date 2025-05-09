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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String favoriteColor = request.getParameter("favcol");
        String dobString = request.getParameter("dateOfBirth");

        // Validate required fields
        if (email == null || email.trim().isEmpty() ||
            firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            dobString == null || dobString.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        // Validate email format
        if (!ValidationUtil.isValidEmail(email)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email format.");
            return;
        }

        // Validate password strength
        if (!ValidationUtil.isValidPassword(password)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password is too weak.");
            return;
        }

        // Check if email already exists
        try {
            if (userDAO.findByEmail(email) != null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email already exists.");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            return;
        }

        // Parse date of birth
        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dobString);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date of birth.");
            return;
        }

        // Hash password
        String hashedPassword = PasswordUtil.hashPassword(password);

        LocalDateTime now = LocalDateTime.now();

        try {
            int newId = userDAO.getNextId(); // Assumes auto-increment or similar logic

            User newUser = new User(newId, email, firstName, lastName, hashedPassword, gender, favoriteColor, dateOfBirth, now, now, "user", true);

            userDAO.createUser(newUser);

            // Registration successful
            response.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

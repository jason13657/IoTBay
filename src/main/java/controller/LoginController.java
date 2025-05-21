package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;
import utils.PasswordUtil;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Security: Always use generic error unless specific reason to be more verbose
        String genericError = "{\"message\": \"Invalid email or password.\"}";
        response.setContentType("application/json");

        try {
            // Already logged in check (optional)
            HttpSession existingSession = request.getSession(false);
            if (existingSession != null && existingSession.getAttribute("user") != null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"message\": \"You are already logged in.\"}");
                return;
            }

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = userDAO.getUserByEmail(email);

            // 1. User not found
            if (user == null) {
                // Optionally, increment failed login attempts here (by IP/email)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(genericError);
                return;
            }

            // 2. Account locked (too many failed attempts)
            if (user.isLocked()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Your account is locked due to too many failed login attempts. Please reset your password or contact support.\"}");
                return;
            }

            // 3. Account inactive or suspended
            if (!user.isActive()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Your account is inactive or suspended. Please contact support.\"}");
                return;
            }

            // 4. Email not verified
            if (!user.isEmailVerified()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Please verify your email before logging in.\"}");
                return;
            }

            // 5. Password expired
            if (user.isPasswordExpired()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Your password has expired. Please reset your password.\"}");
                return;
            }

            // 6. Password check (use hashed password)
            if (!PasswordUtil.checkPassword(password, user.getPassword())) {
                // Optionally, increment failed login attempts here
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(genericError);
                return;
            }

            // 7. Multi-factor authentication required (stub, expand as needed)
            if (user.isMfaEnabled() && !user.isMfaVerified()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Multi-factor authentication required.\"}");
                // Optionally, redirect to MFA verification page.
                return;
            }

            // -- SUCCESSFUL LOGIN --

            // Reset failed login attempts (if you track them)
            // userDAO.resetFailedAttempts(user.getId());

            // Create access log
            LocalDateTime now = LocalDateTime.now();
            AccessLog accessLog = new AccessLog(0, user.getId(), "User " + user.getEmail() + " logged in", now);
            accessLogDAO.createAccessLog(accessLog);

            // Set session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect to index.jsp (with context path)
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Login failed due to a server error. Please try again later.\"}");
        }
    }
}

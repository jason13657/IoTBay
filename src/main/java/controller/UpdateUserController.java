package controller;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/manage/users/update")
public class UpdateUserController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to connect to database", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("user_id"));
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");
            String favoriteColor = request.getParameter("favoriteColor");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String createdAtStr = request.getParameter("createdAt");
            String updatedAtStr = request.getParameter("updatedAt");
            String role = request.getParameter("role");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
            LocalDateTime createdAt = LocalDateTime.parse(createdAtStr);
            LocalDateTime updatedAt = LocalDateTime.parse(updatedAtStr);

            User updatedUser = new User(
                id,
                email,
                firstName,
                lastName,
                password,
                gender,
                favoriteColor,
                dateOfBirth,
                createdAt,
                updatedAt,
                role,
                isActive
            );

            userDAO.updateUser(id, updatedUser);

            response.sendRedirect(request.getContextPath() + "/manage/users");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to update user: " + e.getMessage() + "\"}");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) return false;

        User user = (User) userObj;
        return "staff".equalsIgnoreCase(user.getRole());
    }
}

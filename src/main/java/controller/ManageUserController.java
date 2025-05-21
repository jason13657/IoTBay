package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;

@WebServlet("/manage/users")
public class ManageUserController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            redirectToErrorPage(request, response, "Access denied: You must be an admin to access the manage users page.");
            return;
        }

        try {
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/manage-users.jsp").forward(request, response);

        } catch (SQLException e) {
            redirectToErrorPage(request, response, "Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            redirectToErrorPage(request, response, "Access denied: You must be an admin to perform this action.");
            return;
        }

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");
            String favoriteColor = request.getParameter("favoriteColor");
            String dob = request.getParameter("dateOfBirth");
            String role = request.getParameter("role");

            if (email == null || password == null || firstName == null || lastName == null ||
                gender == null || dob == null || role == null) {
                redirectToErrorPage(request, response, "Missing required fields.");
                return;
            }

            User user = new User(
                0,
                email,
                firstName,
                lastName,
                password,
                gender,
                favoriteColor,
                LocalDate.parse(dob),
                LocalDateTime.now(),
                LocalDateTime.now(),
                role,
                true
            );

            userDAO.createUser(user);
            response.sendRedirect(request.getContextPath() + "/manage/users");

        } catch (SQLException e) {
            redirectToErrorPage(request, response, "Database error: " + e.getMessage());
        } catch (Exception e) {
            redirectToErrorPage(request, response, "Invalid input: " + e.getMessage());
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

    private void redirectToErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}

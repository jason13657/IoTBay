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
import javax.servlet.http.HttpSession;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;

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
            String phone = request.getParameter("phone");
            String postalCode = request.getParameter("postalCode");
            String addressLine1 = request.getParameter("addressLine1");
            String addressLine2 = request.getParameter("addressLine2");
            String paymentMethod = request.getParameter("paymentMethod");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String role = request.getParameter("role");
            String isActiveStr = request.getParameter("isActive");

            // Parse date of birth
            LocalDate dateOfBirth = (dateOfBirthStr != null && !dateOfBirthStr.isEmpty())
                    ? LocalDate.parse(dateOfBirthStr)
                    : null;

            // Set createdAt and updatedAt to the current time
            LocalDateTime now = LocalDateTime.now();

            // Parse isActive (checkbox or boolean string)
            boolean isActive = isActiveStr != null &&
                    (isActiveStr.equalsIgnoreCase("true") || isActiveStr.equalsIgnoreCase("on") || isActiveStr.equals("1"));

            // Construct the User object (id is set to 0, since updateUser uses the id parameter)
            User updatedUser = new User(
                0, // id (actual id is passed separately)
                email,
                password,
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
                role,
                isActive
            );

            userDAO.updateUser(id, updatedUser);

            response.sendRedirect(request.getContextPath() + "/manage/users");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to update user: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid input: " + e.getMessage() + "\"}");
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

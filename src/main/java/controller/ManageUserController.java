package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
<<<<<<< HEAD

import com.google.gson.Gson;
=======
>>>>>>> origin/main

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
<<<<<<< HEAD
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
=======
>>>>>>> origin/main

        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }

        try {
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/views/manage-users.jsp").forward(request, response);

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< HEAD
=======

>>>>>>> origin/main
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
<<<<<<< HEAD
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
=======
>>>>>>> origin/main

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");
            String favoriteColor = request.getParameter("favoriteColor");
            String dob = request.getParameter("dateOfBirth");
            String role = request.getParameter("role");

            if (email == null || password == null || firstName == null || lastName == null || gender == null || dob == null || role == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            User user = new User(
                    0, // ID will be auto-generated
                    email,
                    password,
                    firstName,
                    lastName,
                    null, // phone
                    null, // postalCode
                    null, // addressLine1
                    null, // addressLine2
                    LocalDate.parse(dob),
                    null, // paymentMethod
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    role,
                    true // isActive
            );
            userDAO.createUser(user);
            response.sendRedirect(request.getContextPath() + "/manage/users");

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
<<<<<<< HEAD
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        String idParam = request.getParameter("id");
        if (idParam == null) {
=======
        } catch (Exception e) {
>>>>>>> origin/main
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid input: " + e.getMessage() + "\"}");
        }
    }

<<<<<<< HEAD
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing user id\"}");
            return;
        }
=======
>>>>>>> origin/main

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) return false;

        User user = (User) userObj;
        return "staff".equalsIgnoreCase(user.getRole());
    }
<<<<<<< HEAD

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) return false;

        User user = (User) userObj;
        return "staff".equalsIgnoreCase(user.getRole());
    }
}
=======
}
>>>>>>> origin/main

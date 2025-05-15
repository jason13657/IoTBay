package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;

@WebServlet("/api/manage/users")
public class ManageUserController extends HttpServlet{
    private UserDAO userDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String emailParam = request.getParameter("email");

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                User user = userDAO.getUserById(id);
                if (user != null) {
                    String json = gson.toJson(user);
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            } else if (emailParam != null) {
                List<User> users = userDAO.getUsersByEmail(emailParam);
                if (users.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"No users found with this email\"}");
                } else {
                    String json = gson.toJson(users);
                    response.getWriter().write(json);
                }
            } else {
                List<User> users = userDAO.getAllUsers();
                String json = gson.toJson(users);
                response.getWriter().write(json);
            }
    } catch (SQLException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
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
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);

        try {
            userDAO.createUser(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"User created successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing user id\"}");
            return;
        }

        int id = Integer.parseInt(idParam);
        BufferedReader reader = request.getReader();
        User updatedUser = gson.fromJson(reader, User.class);

        try {
            userDAO.updateUser(id, updatedUser);
            response.getWriter().write("{\"message\": \"User updated successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }

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

        int id = Integer.parseInt(idParam);

        try {
            userDAO.deleteUser(id);
            response.getWriter().write("{\"message\": \"User deleted successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) return false;

        User user = (User) userObj;
        return "admin".equalsIgnoreCase(user.getRole());
    }
}
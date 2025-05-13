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

import com.google.gson.Gson;

import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private UserDAO userDAO;

    private final Gson gson = new Gson();

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            User user = userDAO.getUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                
                // Successful login
                AccessLog accessLog = new AccessLog(0, user.getId(), "Login successful", LocalDateTime.now());

                accessLogDAO.addAccessLog(accessLog);
                
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(user));
            } else {
                // Invalid credentials
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Invalid email or password\"}");
            }
            
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }

    
}
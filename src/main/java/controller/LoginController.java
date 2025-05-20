package controller;


import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

                    if (user == null) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"message\": \"User not found\"}");
                        return;
                    }

                    if (!user.getPassword().equals(password)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"message\": \"Incorrect password\"}");
                        return;
                    }
                    LocalDateTime now = LocalDateTime.now();
                    AccessLog accessLog = new AccessLog(0, user.getId(), "User "+user.getEmail()+" Logged in" , now);
                    accessLogDAO.createAccessLog(accessLog);

                    // Successful login
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Login successful\"}");
                    response.sendRedirect("/index.jsp");
                } catch (SQLException e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
                    response.sendRedirect("/index.jsp");

        }
    }
}
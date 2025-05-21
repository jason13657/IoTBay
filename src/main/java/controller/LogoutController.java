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
import dao.interfaces.AccessLogDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private AccessLogDAO accessLogDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Don't create if not exists
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // Log the logout event
                LocalDateTime now = LocalDateTime.now();
                AccessLog accessLog = new AccessLog(0, user.getId(), "User " + user.getEmail() + " Logged out", now);
                try {
                    accessLogDAO.createAccessLog(accessLog);
                } catch (SQLException e) {
                    // Optionally log the error
                    e.printStackTrace();
                }
            }
            session.invalidate();
        }
        // Redirect to login page (or main page)
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;

@WebServlet("/api/user/deleteAccount")
public class DeleteAccountController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAOImpl userDAO;
    private Connection connection; // Add a Connection instance

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Establish database connection in init()
            connection = DBConnection.getConnection();
            // Initialize UserDAOImpl with the connection
            userDAO = new UserDAOImpl(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error initializing database connection", e);
        }
    }

    @Override
    public void destroy() {
        // Close the database connection when the servlet is destroyed
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Please login first.");
            return;
        }

        String userId = (String) session.getAttribute("userId");

        try {
            boolean deleted = userDAO.deleteUserById(userId); // Modified to use the instance connection
            if (deleted) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }
}
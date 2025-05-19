package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

@WebServlet("/api/accessLog")
public class AccesslogController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private static final Logger logger = Logger.getLogger(AccesslogController.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConnection.getConnection();
            this.accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed in AccesslogController.init()", e);
            throw new ServletException("Database connection failed.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Session check: Only allow logged-in users
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // 2. Date parameter check and validation
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        LocalDate startDate = null, endDate = null;
        LocalDate today = LocalDate.now();

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr);
            }
        } catch (DateTimeParseException e) {
            // Invalid date format
            request.setAttribute("error", "Date format is invalid. Please use YYYY-MM-DD.");
            request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
            return;
        }

        // Prevent searching for logs in the future
        if ((startDate != null && startDate.isAfter(today)) || (endDate != null && endDate.isAfter(today))) {
            request.setAttribute("error", "You cannot search for access logs in the future.");
            request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
            return;
        }

        // Prevent endDate before startDate
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            request.setAttribute("error", "End date cannot be before start date.");
            request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
            return;
        }

        // Prevent date ranges that are too large (e.g., more than 1 year)
        if (startDate != null && endDate != null && startDate.plusYears(1).isBefore(endDate)) {
            request.setAttribute("error", "Date range is too large. Please select a range within 1 year.");
            request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
            return;
        }

        // 3. Retrieve only the current user's logs from the database
        List<AccessLog> accessLogList = null;
        try {
            if (startDate != null && endDate != null) {
                accessLogList = accessLogDAO.getAccessLogsByUserIdAndDateRange(user.getId(), startDate, endDate);
            } else {
                accessLogList = accessLogDAO.getAccessLogsByUserId(user.getId());
            }
            request.setAttribute("accessLogList", accessLogList);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve access logs for userId " + user.getId(), e);
            request.setAttribute("error", "An error occurred while retrieving access logs.");
        }

        // 4. Forward to JSP (no edit/delete functionality)
        request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
    }

    // POST, PUT, DELETE are not implemented (users cannot edit/delete their access logs)
}

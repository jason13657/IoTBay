package controller;

import com.google.gson.Gson;

import dao.AccessLogDAOImpl;
import dao.interfaces.AccessLogDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/manage/access-logs")
public class ManageAccessLogController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
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
        String userIdParam = request.getParameter("userId");

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                AccessLog log = accessLogDAO.getAccessLogById(id);
                if (log != null) {
                    response.getWriter().write(gson.toJson(log));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Access log not found\"}");
                }
            } else if (userIdParam != null) {
                int userId = Integer.parseInt(userIdParam);
                List<AccessLog> logs = accessLogDAO.getAccessLogsByUserId(userId);
                response.getWriter().write(gson.toJson(logs));
            } else {
                List<AccessLog> logs = accessLogDAO.getAllAccessLogs();
                response.getWriter().write(gson.toJson(logs));
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
        try {
            BufferedReader reader = request.getReader();
            AccessLog log = gson.fromJson(reader, AccessLog.class);
            accessLogDAO.createAccessLog(log);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(log));
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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            accessLogDAO.deleteAccessLog(id);
            response.getWriter().write("{\"message\": \"Access log deleted successfully\"}");
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
        return "staff".equalsIgnoreCase(user.getRole());
    }
}
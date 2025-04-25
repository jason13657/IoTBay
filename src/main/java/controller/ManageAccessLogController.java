package controller;

import com.google.gson.Gson;
import dao.interfaces.AccessLogDAO;
import dao.stub.AccessLogDAOStub;
import model.AccessLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/manage/access-logs")
public class ManageAccessLogController extends HttpServlet {
    private AccessLogDAO accessLogDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        accessLogDAO = new AccessLogDAOStub();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            accessLogDAO.deleteAccessLog(id);
            response.getWriter().write("{\"message\": \"Access log deleted successfully\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}
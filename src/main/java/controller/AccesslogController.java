package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AccessLogDAOImpl;
import model.AccessLog;
import model.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/accessLogs")
public class AccesslogController extends HttpServlet {
    private AccessLogDAOImpl accessLogDAO;
    private Connection connection;

    @Override
    public void init() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/yourDB");
            connection = ds.getConnection();
            accessLogDAO = new AccessLogDAOImpl(connection);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"Login required\"}");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        try {
            List<AccessLog> accessLogs = accessLogDAO.getAccessLogsByUserId(userId);

            // Filter out only loginTime
            List<Map<String, Object>> filteredLogs = new ArrayList<>();
            for (AccessLog log : accessLogs) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("loginTime", log.getLoginTime());
                filteredLogs.add(entry);
            }

            resp.setContentType("application/json");
            new ObjectMapper().writeValue(resp.getOutputStream(), filteredLogs);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection; // DBConnection import
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccessLogDAOImpl;
import db.DBConnection;

@WebServlet("/logout")
public class LogoutServletController extends HttpServlet {

    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() {
        try {
            Connection conn = DBConnection.getConnection();
            accessLogDAO = new AccessLogDAOImpl(conn);
        } catch (Exception e) {
            e.printStackTrace();
            // 필요시: throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Integer logId = (Integer) session.getAttribute("currentLogId");
            if (logId != null) {
                try {
                    // Timestamp로 현재 시간 전달
                    accessLogDAO.updateLogoutTime(logId, new Timestamp(System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login?logout=true");
    }
}

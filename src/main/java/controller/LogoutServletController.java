package controller;

import dao.AccessLogDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/logout")
public class LogoutServletController extends HttpServlet {
    
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() {
        accessLogDAO = new AccessLogDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Integer logId = (Integer) session.getAttribute("currentLogId");
            if (logId != null) {
                try {
                    accessLogDAO.updateLogoutTime(logId, new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login?logout=true");
    }
}

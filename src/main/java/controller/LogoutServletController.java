package controller;

import dao.AccessLogDAOImpl;
import util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/logout")
public class LogoutServletController extends HttpServlet {
    
    private static final Logger logger = LogManager.getLogger(LogoutServletController.class);
    private AccessLogDAOImpl accessLogDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBUtil.getConnection();
            accessLogDAO = new AccessLogDAOImpl(conn);
        } catch (SQLException e) {
            logger.error("DAO 초기화 실패", e);
            throw new ServletException("서버 초기화 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null)
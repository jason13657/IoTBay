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
    
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // DAO 초기화 (DB 연결은 메서드 내부에서 처리할 수도 있음)
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // 로그인 안 된 상태면 401 Unauthorized 또는 로그인 페이지로 리다이렉트
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Please login first.");
            return;
        }

        String userId = (String) session.getAttribute("userId");

        try (Connection conn = DBConnection.getConnection()) {
            boolean deleted = userDAO.deleteUserById(conn, userId);
            if (deleted) {
                // 삭제 성공 시 세션 무효화 후 메인 또는 로그인 페이지로 리다이렉트
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                // 삭제 실패 시 500 에러
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }
}

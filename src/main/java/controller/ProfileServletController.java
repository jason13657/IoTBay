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

import dao.OrderDAO;
import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

@WebServlet("/profile")
public class ProfileServletController extends HttpServlet {

    private UserDAOImpl userDAO;

    @Override
    public void init() {
        try {
            Connection conn = DBConnection.getConnection();
            userDAO = new UserDAOImpl(conn);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // 필요하다면: throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // [추가] 회원 탈퇴 액션 체크
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
        handleDeleteRequest(req, resp, user, session);
        return;
    }

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // 유효성 검사
        String error = ValidationUtil.validateProfileUpdate(firstName, lastName, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        try {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);

            if (currentPassword != null && !currentPassword.isEmpty()) {
                if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
                    req.setAttribute("error", "현재 비밀번호가 올바르지 않습니다.");
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                    return;
                }

                error = ValidationUtil.validatePasswordChange(newPassword, confirmPassword);
                if (error != null) {
                    req.setAttribute("error", error);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                    return;
                }

                user.setPassword(PasswordUtil.hashPassword(newPassword));
            }

            userDAO.updateUser(user.getId(), user);
            session.setAttribute("user", user);

            req.setAttribute("message", "정보가 성공적으로 업데이트되었습니다.");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "정보 업데이트 중 오류가 발생했습니다.");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        }
    }

    private void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp, 
                                User user, HttpSession session) 
        throws ServletException, IOException {
    
    // 1. 비밀번호 확인
    String deletePassword = req.getParameter("deletePassword");
    if (!PasswordUtil.verifyPassword(deletePassword, user.getPassword())) {
        req.setAttribute("error", "비밀번호가 일치하지 않습니다.");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        return;
    }

    Connection conn = null;
    try {
        // 2. 트랜잭션 시작
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        // 3. 주문 상태 변경
        OrderDAO orderDAO = new OrderDAOImpl(conn);
        orderDAO.cancelAllOrdersByUserId(user.getId());

        // 4. 사용자 삭제
        UserDAO userDAO = new UserDAOImpl(conn);
        boolean isDeleted = userDAO.deleteUser(user.getId());
        
        if (!isDeleted) {
            throw new SQLException("사용자 삭제 실패");
        }

        // 5. 트랜잭션 커밋
        conn.commit();

        // 6. 세션 무효화 및 리다이렉트
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/");

    } catch (SQLException | ClassNotFoundException e) {
        // 트랜잭션 롤백
        try { if (conn != null) conn.rollback(); } 
        catch (SQLException ex) { ex.printStackTrace(); }
        
        req.setAttribute("error", "탈퇴 처리 중 오류가 발생했습니다.");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    } finally {
        // 리소스 정리
        try { 
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
}

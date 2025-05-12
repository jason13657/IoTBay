package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDAO;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.AccessLogDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;
import model.AccessLog;
import utils.PasswordUtil;
import utils.ValidationUtil;

@WebServlet("/api/profile")
public class ProfileController extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        String searchDate = req.getParameter("searchDate");

        try (Connection conn = DBConnection.getConnection()) {
            AccessLogDAO accessLogDAO = new AccessLogDAOImpl(conn);
            List<AccessLog> logs = (searchDate != null && !searchDate.isEmpty())
                    ? accessLogDAO.getAccessLogsByUserIdAndDate(user.getId(), LocalDate.parse(searchDate))
                    : accessLogDAO.getAccessLogsByUserId(user.getId());

            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), logs);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getOutputStream(), "로그 데이터를 가져오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        BufferedReader reader = req.getReader();
        ProfileUpdateRequest updateRequest = mapper.readValue(reader, ProfileUpdateRequest.class);

        String error = ValidationUtil.validateProfileUpdate(updateRequest.firstName, updateRequest.lastName, updateRequest.phone);
        if (error != null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getOutputStream(), error);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(conn);

            currentUser.setFirstName(updateRequest.firstName);
            currentUser.setLastName(updateRequest.lastName);
            currentUser.setPhone(updateRequest.phone);

            if (updateRequest.currentPassword != null && !updateRequest.currentPassword.isEmpty()) {
                if (!PasswordUtil.verifyPassword(updateRequest.currentPassword, currentUser.getPassword())) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    mapper.writeValue(resp.getOutputStream(), "현재 비밀번호가 올바르지 않습니다.");
                    return;
                }

                error = ValidationUtil.validatePasswordChange(updateRequest.newPassword, updateRequest.confirmPassword);
                if (error != null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    mapper.writeValue(resp.getOutputStream(), error);
                    return;
                }

                currentUser.setPassword(PasswordUtil.hashPassword(updateRequest.newPassword));
            }

            userDAO.updateUser(currentUser.getId(), currentUser);
            session.setAttribute("user", currentUser);

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getOutputStream(), "정보가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getOutputStream(), "정보 업데이트 중 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        DeleteRequest deleteRequest = mapper.readValue(req.getReader(), DeleteRequest.class);

        try (Connection conn = DBConnection.getConnection()) {
            if (!PasswordUtil.verifyPassword(deleteRequest.password, user.getPassword())) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                mapper.writeValue(resp.getOutputStream(), "비밀번호가 일치하지 않습니다.");
                return;
            }

            conn.setAutoCommit(false);

            OrderDAO orderDAO = new OrderDAO(conn);
            orderDAO.cancelAllOrdersByUserId(user.getId());

            UserDAO userDAO = new UserDAOImpl(conn);

            boolean deleted = userDAO.deleteUser(user.getId());

            if (!deleted) throw new SQLException("사용자 삭제 실패");

            conn.commit();
            session.invalidate();
            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getOutputStream(), "계정이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getOutputStream(), "탈퇴 처리 중 오류가 발생했습니다.");
        }
    }

    // Request DTOs
    static class ProfileUpdateRequest {
        public String firstName;
        public String lastName;
        public String phone;
        public String currentPassword;
        public String newPassword;
        public String confirmPassword;
    }

    static class DeleteRequest {
        public String password;
    }
}

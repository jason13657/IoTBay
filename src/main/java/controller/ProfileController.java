package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AccessLogDAOImpl;
import dao.UserDAOImpl;
import dao.interfaces.AccessLogDAO;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.AccessLog;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/profile")
public class ProfileController extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    // 1. 회원정보 조회 (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        String searchDate = req.getParameter("searchDate");

        try (Connection conn = DBConnection.getConnection()) {
            AccessLogDAO accessLogDAO = new AccessLogDAOImpl(conn);

            // 1) 내 정보 조회만 요청한 경우
            if (searchDate == null) {
                resp.setContentType("application/json");
                mapper.writeValue(resp.getOutputStream(), user);
                return;
            }

            // 2) access log 조회 (날짜별)
            List<AccessLog> logs;
            if (!searchDate.isEmpty()) {
                logs = accessLogDAO.getAccessLogsByUserIdAndDate(user.getId(), LocalDate.parse(searchDate));
            } else {
                logs = accessLogDAO.getAccessLogsByUserId(user.getId());
            }
            resp.setContentType("application/json");
            mapper.writeValue(resp.getOutputStream(), logs);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getOutputStream(), "로그 데이터를 가져오는 중 오류가 발생했습니다.");
        }
    }

    // 2. 회원정보 수정 (PUT)
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

        // 이름, 연락처 등 유효성 검사
        String error = ValidationUtil.validateProfileUpdate(updateRequest.fullName, updateRequest.phone, "dummy");
        if (error != null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getOutputStream(), error);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(conn);

            // 필드 업데이트
            currentUser.setFullName(updateRequest.fullName);
            currentUser.setPhone(updateRequest.phone);
            currentUser.setPostalCode(updateRequest.postalCode);
            currentUser.setAddressLine1(updateRequest.addressLine1);
            currentUser.setAddressLine2(updateRequest.addressLine2);
            currentUser.setDateOfBirth(updateRequest.dateOfBirth);
            currentUser.setPaymentMethod(updateRequest.paymentMethod);

            // 비밀번호 변경 요청이 있을 때만
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

    // 3. 회원 탈퇴 (DELETE)
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
            UserDAO userDAO = new UserDAOImpl(conn);

            // 비밀번호 확인
            if (!PasswordUtil.verifyPassword(deleteRequest.password, user.getPassword())) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                mapper.writeValue(resp.getOutputStream(), "비밀번호가 일치하지 않습니다.");
                return;
            }

            boolean deleted = userDAO.deleteUser(user.getId());
            if (!deleted) throw new Exception("사용자 삭제 실패");

            session.invalidate();
            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getOutputStream(), "계정이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getOutputStream(), "탈퇴 처리 중 오류가 발생했습니다.");
        }
    }

    // DTOs
    static class ProfileUpdateRequest {
        public String fullName;
        public String phone;
        public String postalCode;
        public String addressLine1;
        public String addressLine2;
        public java.time.LocalDate dateOfBirth;
        public String paymentMethod;
        public String currentPassword;
        public String newPassword;
        public String confirmPassword;
    }

    static class DeleteRequest {
        public String password;
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;
import utils.ValidationUtil;

@WebServlet("/api/Profiles")
public class UserProfileController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = DBConnection.getConnection();
            userDAO = new UserDAOImpl(conn);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("DB init failed", e);
        }
    }

    // GET: Show current user's profile (for profile page)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                //brings user to profile page
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        // update user info
        try {
            User freshUser = userDAO.getUserById(user.getId());
            request.setAttribute("user", freshUser);
            request.getRequestDispatcher("/Profiles.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load profile.");
        }
    }

    // POST: Update current user's profile
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("user") : null;
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 파라미터 추출
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String postalCode = request.getParameter("postalCode");
        String addressLine1 = request.getParameter("addressLine1");
        String addressLine2 = request.getParameter("addressLine2");
        String dobString = request.getParameter("dateOfBirth");
        String paymentMethod = request.getParameter("paymentMethod");

        // 유효성 검사
        String profileError = ValidationUtil.validateRegisterUserProfile(
            firstName, lastName, phone, postalCode, addressLine1
        );
        if (profileError != null) {
            request.setAttribute("error", profileError);
            doGet(request, response); // Error page, 
            return;
        }

        LocalDate dateOfBirth = null;
        if (dobString != null && !dobString.trim().isEmpty()) {
            String dobError = ValidationUtil.validateBirthDate(dobString);
            if (dobError != null) {
                request.setAttribute("error", dobError);
                doGet(request, response);
                return;
            }
            dateOfBirth = LocalDate.parse(dobString);
        }

        try {
            // 기존 정보 유지, 변경된 정보만 업데이트
            User updatedUser = new User(
                sessionUser.getId(),
                sessionUser.getEmail(), // 이메일은 변경 불가(실무적)
                sessionUser.getPassword(),
                firstName,
                lastName,
                phone,
                postalCode,
                addressLine1,
                addressLine2,
                dateOfBirth,
                paymentMethod,
                sessionUser.getCreatedAt(),
                java.time.LocalDateTime.now(),
                sessionUser.getRole(),
                sessionUser.isActive()
            );
            userDAO.updateUser(updatedUser.getId(), updatedUser);
            // 세션 정보도 갱신
            session.setAttribute("user", updatedUser);
            // 성공 메시지와 함께 프로필 페이지로 이동
            request.setAttribute("success", "Profile updated successfully.");
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to update profile: " + e.getMessage());
            doGet(request, response);
        }
    }
}

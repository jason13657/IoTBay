package controller;

import dao.UserDAOImpl;
import dao.interfaces.UserDAO;
import db.DBConnection;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        try {
            Connection connection = DBConnection.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8"); // 한글 처리

            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            String favoriteColor = request.getParameter("favcol");
            String dobString = request.getParameter("dateOfBirth");

            LocalDate dateOfBirth = LocalDate.parse(dobString);
            LocalDateTime now = LocalDateTime.now();

            // ID는 DB에서 자동 증가하거나, 별도로 생성 로직이 필요
            int newId = userDAO.getNextId(); // getNextId()는 따로 구현되어야 함

            User newUser = new User(newId, email, firstName, lastName, password, gender, favoriteColor, dateOfBirth, now, now, "user", true);

            userDAO.addUser(newUser); // DAO에 addUser 메서드 필요

            // 성공 시
            response.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

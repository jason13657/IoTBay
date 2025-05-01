package controller;

import dao.UserDAOImpl;
import model.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServletController extends HttpServlet {
    
    private UserDAOImpl userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl(); // DAO 인스턴스 생성 (필요시 DI 적용)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String phone = req.getParameter("phone");
        String userType = req.getParameter("userType");

        // 서버측 검증
        String error = ValidationUtil.validateRegistration(fullName, email, password, confirmPassword, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            if (userDAO.findByEmail(email) != null) {
                req.setAttribute("error", "이미 존재하는 이메일입니다.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setPhone(phone);
            user.setUserType(userType);

            userDAO.create(user);

            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "회원가입 중 오류가 발생했습니다.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}

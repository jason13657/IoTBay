package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PaymentDAO;
import db.DBConnection;
import model.User;

@WebServlet("/api/payment/create")
public class PaymentCreateController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        try {
            DBConnection db = new DBConnection();
            paymentDAO = new PaymentDAO(db.getConnection());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize PaymentDAO", e);
        }
    }

    // 결제 생성 폼 표시
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");

        if (orderId == null || orderId.trim().isEmpty()) {
            request.setAttribute("errorMessage", "유효하지 않은 주문입니다.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("orderId", orderId);
        request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
    }

    // 결제 생성 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            boolean success = paymentDAO.processCreatePayment(request, user);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/payment/history");
            } else {
                request.setAttribute("errorMessage", "결제 처리에 실패했습니다. 다시 시도해주세요.");
                request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace(); // 추후 log로 변경
            request.setAttribute("errorMessage", "서버 오류로 결제 처리에 실패했습니다.");
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
        }
    }
}

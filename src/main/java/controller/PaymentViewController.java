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



@WebServlet("/payment/view")
public class PaymentViewController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        try {
            DBConnection db = new DBConnection();
            paymentDAO = new PaymentDAO(db.getConnection());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 결제 상세 조회
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String paymentIdStr = request.getParameter("paymentId");
        // ... (기존 showViewPaymentPage 메소드 내용 복사)
        // 조회 실패 시: request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
        // 성공 시: request.setAttribute("payment", payment); request.getRequestDispatcher("/payment/viewPayment.jsp").forward(request, response);
    }
}

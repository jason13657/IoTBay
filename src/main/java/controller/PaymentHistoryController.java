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
@WebServlet("/payment/history")
public class PaymentHistoryController extends HttpServlet {
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

    // 결제 내역 조회 및 검색
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // ... (기존 listPaymentHistory 메소드 내용 복사)
        // 결과: request.setAttribute("paymentList", paymentList); request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
    }
}


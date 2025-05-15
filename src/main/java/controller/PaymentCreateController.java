package controller;

import dao.PaymentDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import utils.ValidationUtil;
import db.DBConnection;


@WebServlet("/payment/create")
public class PaymentCreateController extends HttpServlet {

    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

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
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String paymentMethod = request.getParameter("paymentMethod"); // "CARD" or "PAYPAL"
            double amount = Double.parseDouble(request.getParameter("amount"));

            String cardHolderName = request.getParameter("cardHolderName");
            String cardNumber = request.getParameter("cardNumber");
            String cardExpiryDate = request.getParameter("cardExpiryDate");
            String cardCVV = request.getParameter("cardCVV");

            boolean valid = true;
            String status = "FAILED";

            if ("CARD".equalsIgnoreCase(paymentMethod)) {
                if (cardHolderName == null || cardHolderName.isEmpty() ||
                    cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 16 ||
                    cardExpiryDate == null || !cardExpiryDate.matches("(0[1-9]|1[0-2])/\\d{4}") ||
                    cardCVV == null || !cardCVV.matches("\\d{3,4}")) {

                    valid = false;
                }
            }

            if (!valid) {
                request.setAttribute("errorMessage", "유효하지 않은 카드 정보입니다.");
                request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
                return;
            }

            // 실제 결제 로직은 생략하고 DB에 삽입 처리
            status = "SUCCESS";
            boolean success = paymentDAO.insertPayment(orderId, user.getUserId(), amount, paymentMethod, status);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/payment/history");
            } else {
                request.setAttribute("errorMessage", "결제 저장 중 오류가 발생했습니다.");
                request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "서버 오류가 발생했습니다.");
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
        }
    }
}


package controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//
import dao.PaymentDAO;
import db.DBConnection;
//
import model.User;
import model.Payment;
import model.Order;
import model.Product;
//
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.logging.Level;
import java.util.logging.Logger;


import utils.validation.ValidationUtils; // this is for validating the user input 

@WebServlet("api/payment/edit")
public class PaymentEditController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        try {
            DBConnection db = new DBConnection();
            paymentDAO = new PaymentDAO(db.getConnection());
        } catch (Exception e) {
            throw new ServletException("Errors occurred while initializing PaymentDAO", e);
        }
    }

    // 결제 수정 폼 표시
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String paymentIdStr = request.getParameter("paymentId");
        // ... (기존 showEditPaymentPage 메소드 내용 복사)
        // 조회 실패 시: request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
        // 성공 시: request.setAttribute("payment", payment); request.getRequestDispatcher("/payment/editPayment.jsp").forward(request, response);
    }

    // 결제 수정 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // ... (기존 processUpdatePayment 메소드 내용 복사)
        // 성공 시: response.sendRedirect(request.getContextPath() + "/payment/history");
        // 실패 시: request.getRequestDispatcher("/payment/editPayment.jsp").forward(request, response);
    }
}

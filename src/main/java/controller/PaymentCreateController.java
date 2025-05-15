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
import model.Payment;
import model.Order;
import model.Product;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@WebServlet("/payment/create")
public class PaymentCreateController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        try {
            DBConnector db = new DBConnector();
            paymentDAO = new PaymentDAO(db.getConnection());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 결제 생성 폼 표시
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        request.setAttribute("orderId", orderId);
        request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
    }

    // 결제 생성 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // ... (기존 processCreatePayment 메소드 내용 복사)
        // 생성 성공 시: response.sendRedirect(request.getContextPath() + "/payment/history");
        // 실패 시: request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
    }
}

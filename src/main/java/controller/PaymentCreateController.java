package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

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
        // 세션 및 로그인 체크
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");

        try {
            // 결제 생성 처리
            boolean success = paymentDAO.processCreatePayment(request, user);

            if (success) {
                // 결제 성공 시 결제 내역 페이지로 이동
                response.sendRedirect(request.getContextPath() + "/payment/history");
            } else {
                // 결제 실패 시 입력값 유지 및 에러 메시지 표시
                request.setAttribute("errorMessage", "결제 처리에 실패했습니다. 입력값을 확인하고 다시 시도해주세요.");
                request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // 서버 오류 발생 시 에러 메시지 표시 및 로그 기록
            e.printStackTrace(); // 실무에서는 로깅 프레임워크로 대체 권장
            request.setAttribute("errorMessage", "서버 오류로 결제 처리에 실패했습니다. 잠시 후 다시 시도해주세요.");
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
        }
    }

    public boolean processCreatePayment(HttpServletRequest request, User user) {
        // 결제 생성 로직 구현
        // ...
        public boolean processCreatePayment(HttpServletRequest request, User user) throws SQLException {
    // 파라미터 추출
    String orderIdStr = request.getParameter("orderId");
    String amountStr = request.getParameter("amount");
    String paymentMethodStr = request.getParameter("paymentMethod");

    // 필수값 검증
    if (orderIdStr == null || amountStr == null || paymentMethodStr == null ||
        orderIdStr.isEmpty() || amountStr.isEmpty() || paymentMethodStr.isEmpty()) {
        return false;
    }

    try {
        int orderId = Integer.parseInt(orderIdStr);
        double amount = Double.parseDouble(amountStr);
        int paymentMethod = Integer.parseInt(paymentMethodStr); // detail_id 컬럼에 저장

        // 결제 상태는 신규 생성 시 "PENDING" 등으로 지정
        String paymentStatus = "PENDING";

        // Payment 객체 생성
        Payment payment = new Payment();
        payment.setUserId(user.getUserId());
        payment.setOrderId(orderId);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(paymentStatus);

        // DB 저장
        createPayment(payment);
        return true;
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    

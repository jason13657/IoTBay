package controller;

@WebServlet("/payment/history")
public class PaymentHistoryController extends HttpServlet {
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

    // 결제 내역 조회 및 검색
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // ... (기존 listPaymentHistory 메소드 내용 복사)
        // 결과: request.setAttribute("paymentList", paymentList); request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
    }
}


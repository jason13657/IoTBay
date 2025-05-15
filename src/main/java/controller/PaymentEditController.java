@WebServlet("/payment/edit")
public class PaymentEditController extends HttpServlet {
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

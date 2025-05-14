package uts.isd.iotbay.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.iotbay.model.Payment;
import uts.isd.iotbay.model.User;
import uts.isd.iotbay.dao.PaymentDAO;
import uts.isd.iotbay.dao.DBConnector;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn; // Hold the connection for the lifecycle of requests handled by this servlet instance.
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            db = new DBConnector();
            conn = db.getConnection(); // Establish connection
            paymentDAO = new PaymentDAO(conn); // Pass connection to DAO
        } catch (ClassNotFoundException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "JDBC Driver not found", e);
            throw new ServletException("JDBC Driver not found", e);
        } catch (SQLException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "Failed to connect to database", e);
            throw new ServletException("Failed to connect to database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        User user = (User) session.getAttribute("user");
        if (user == null && !("somePublicAction".equals(action))) {
            session.setAttribute("errorMessage", "Please login to access payment details.");
            response.sendRedirect(request.getContextPath() + "/login.jsp"); 
            return;
        }

        if (action == null) {
            if(user != null) action = "history";
            else {
                 session.setAttribute("errorMessage", "Please login.");
                 response.sendRedirect(request.getContextPath() + "/login.jsp"); 
                 return;
            }
        }

        try {
            switch (action) {
                case "view":
                    showViewPaymentPage(request, response, user);
                    break;
                case "edit":
                    showEditPaymentPage(request, response, user);
                    break;
                case "create":
                    String orderIdForCreate = request.getParameter("orderId");
                    request.setAttribute("orderId", orderIdForCreate);
                    request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
                    break;
                case "history":
                default:
                    if (user == null) {
                        session.setAttribute("errorMessage", "Please login to view payment history.");
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                        return;
                    }
                    listPaymentHistory(request, response, user);
                    break;
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "SQL Error in doGet", e);
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/payment/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.WARNING, "Invalid ID format in doGet", e);
            request.setAttribute("errorMessage", "Invalid ID format provided.");
            request.getRequestDispatcher("/payment/error.jsp").forward(request, response);
        } catch (Exception e) { 
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "Error in doGet", e);
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/payment/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("errorMessage", "Please login to perform this action.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            if (action == null) {
                session.setAttribute("errorMessage", "Invalid action.");
                response.sendRedirect(request.getContextPath() + "/payment?action=history");
                return;
            }
            switch (action) {
                case "createPayment":
                    processCreatePayment(request, response, user);
                    break;
                case "updatePayment":
                    processUpdatePayment(request, response, user);
                    break;
                case "deletePayment":
                    processDeletePayment(request, response, user);
                    break;
                default:
                    session.setAttribute("errorMessage", "Unknown payment action.");
                    response.sendRedirect(request.getContextPath() + "/payment?action=history");
                    break;
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "SQL Error in doPost", e);
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            // Redirect based on action or to a general error page
            response.sendRedirect(request.getContextPath() + "/payment?action=history&error=db");
        } catch (ParseException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, "Parsing error in doPost", e);
            session.setAttribute("errorMessage", "Invalid data format: " + e.getMessage());
            // Redirect based on action or to a general error page
            response.sendRedirect(request.getContextPath() + "/payment?action=history&error=parse");
        }
    }

    private void processCreatePayment(HttpServletRequest request, HttpServletResponse response, User user)
            throws SQLException, IOException, ParseException, ServletException {
        HttpSession session = request.getSession();

        String orderIdStr = request.getParameter("orderId");
        String paymentMethod = request.getParameter("paymentMethod");
        String cardHolderName = request.getParameter("cardHolderName");
        String cardNumber = request.getParameter("cardNumber");
        String cardExpiryDate = request.getParameter("cardExpiryDate");
        String cardCVV = request.getParameter("cardCVV");
        String amountStr = request.getParameter("amount");

        // Basic Server-Side Validation
        if (orderIdStr == null || orderIdStr.trim().isEmpty() ||
            paymentMethod == null || paymentMethod.trim().isEmpty() ||
            cardHolderName == null || cardHolderName.trim().isEmpty() ||
            cardNumber == null || cardNumber.trim().isEmpty() || // TODO: Add regex for card number
            cardExpiryDate == null || cardExpiryDate.trim().isEmpty() || // TODO: Add regex for MM/YYYY
            cardCVV == null || cardCVV.trim().isEmpty() || // TODO: Add regex for 3-4 digits
            amountStr == null || amountStr.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All payment fields are required.");
            // Preserve entered values for repopulation if desired (not fully shown for brevity)
            request.setAttribute("orderId", orderIdStr);
            // ... set other attributes for cardHolderName, cardNumber etc. from request parameters
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
            return;
        }

        int orderId;
        double amount;
        try {
            orderId = Integer.parseInt(orderIdStr);
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                 request.setAttribute("errorMessage", "Amount must be greater than zero.");
                 request.setAttribute("orderId", orderIdStr);
                 request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
                 return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Order ID or Amount format.");
            request.setAttribute("orderId", orderIdStr);
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
            return;
        }
        
        // TODO: Implement more specific validation for card number, expiry date format (MM/YYYY), CVV (3-4 digits)
        // using regex or a validation utility class.

        Payment payment = new Payment();
        payment.setOrderId(orderId);

        // Get customer ID directly as int from User object
        int customerId = user.getId();
        payment.setCustomerId(customerId);

        // Handle cases where user ID might be considered invalid (e.g., 0 or negative if that's your convention)
        if (customerId <= 0) { // Example check, adjust if your IDs start from 1 and 0 is invalid
             Logger.getLogger(PaymentServlet.class.getName()).log(Level.WARNING, "Invalid User ID for payment processing: " + customerId);
             request.setAttribute("errorMessage", "User session error or invalid user ID. Please login again.");
             request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
             return;
        }

        payment.setPaymentMethod(paymentMethod);
        payment.setCardHolderName(cardHolderName);
        payment.setCardNumber(cardNumber); // Sensitive data
        payment.setCardExpiryDate(cardExpiryDate);
        payment.setCardCVV(cardCVV);       // Highly sensitive - AVOID STORING if possible
        payment.setAmount(amount);
        payment.setPaymentDate(new Date()); // Current date and time
        payment.setPaymentStatus("SAVED");  // Initial status

        int paymentId = paymentDAO.createPayment(payment);

        if (paymentId != -1) {
            session.setAttribute("successMessage", "Payment details saved successfully! Payment ID: " + paymentId);
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
        } else {
            request.setAttribute("errorMessage", "Failed to save payment details. Database error or invalid data.");
            request.setAttribute("orderId", orderIdStr);
            // To repopulate the form, you could set the attempted payment object as an attribute
            // request.setAttribute("paymentAttempt", payment);
            request.getRequestDispatcher("/payment/createPayment.jsp").forward(request, response);
        }
    }
    
    private void listPaymentHistory(HttpServletRequest request, HttpServletResponse response, User user) 
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        
        int customerId = user.getId();

        if (customerId <= 0) { // Example check
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.WARNING, "User ID not available or invalid for listing payment history: " + customerId);
            session.setAttribute("errorMessage", "User session error. Please login again.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String paymentIdQuery = request.getParameter("paymentIdQuery");
        String dateQueryStr = request.getParameter("dateQuery"); // Expected format YYYY-MM-DD from <input type="date">
        java.util.Date dateQuery = null;

        if (dateQueryStr != null && !dateQueryStr.trim().isEmpty()) {
            try {
                dateQuery = new SimpleDateFormat("yyyy-MM-dd").parse(dateQueryStr);
            } catch (ParseException e) {
                Logger.getLogger(PaymentServlet.class.getName()).log(Level.INFO, "Invalid date format for search: " + dateQueryStr, e);
                request.setAttribute("searchErrorMessage", "Invalid date format for search. Please use YYYY-MM-DD.");
                // Keep paymentIdQuery if present
            }
        }

        List<Payment> paymentList;
        // If search parameters are present, use searchPayments, otherwise get all by customerId
        if ((paymentIdQuery != null && !paymentIdQuery.trim().isEmpty()) || dateQuery != null) {
            paymentList = paymentDAO.searchPayments(customerId, paymentIdQuery, dateQuery);
        } else {
            paymentList = paymentDAO.getPaymentsByCustomerId(customerId);
        }
        
        request.setAttribute("paymentList", paymentList);
        // Forward any existing success/error messages from previous actions (e.g., after create/delete redirect)
        if(session.getAttribute("successMessage") != null){
            request.setAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }
        if(session.getAttribute("errorMessage") != null){
            request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }
        
        request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
    }

    private void showViewPaymentPage(HttpServletRequest request, HttpServletResponse response, User user)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        String paymentIdStr = request.getParameter("paymentId");

        if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Payment ID is required to view details.");
            request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response); // Or a generic error page
            return;
        }

        int paymentId = Integer.parseInt(paymentIdStr); // Catch NumberFormatException in main doGet
        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment == null) {
            request.setAttribute("errorMessage", "Payment not found.");
            request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
            return;
        }

        int customerId = user.getId();
        if (customerId <= 0) { // Example check
             session.setAttribute("errorMessage", "User session error. Please login again.");
             response.sendRedirect(request.getContextPath() + "/login.jsp");
             return;
        }
        
        // boolean isAdmin = user.isAdmin(); // Example if User model has isAdmin()
        // if (payment.getCustomerId() != customerId && !isAdmin) { 
        if (payment.getCustomerId() != customerId) { // Simplified: only owner can view
            request.setAttribute("errorMessage", "You are not authorized to view this payment.");
            // Forward to history or an unauthorized page
            // For now, sending to their own history to avoid showing data they shouldn't see.
            listPaymentHistory(request, response, user); 
            return;
        }

        request.setAttribute("payment", payment);
        request.getRequestDispatcher("/payment/viewPayment.jsp").forward(request, response);
    }

    private void showEditPaymentPage(HttpServletRequest request, HttpServletResponse response, User user)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        String paymentIdStr = request.getParameter("paymentId");

        if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Payment ID is required to edit details.");
            request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
            return;
        }

        int paymentId = Integer.parseInt(paymentIdStr); // Catch NumberFormatException in main doGet
        Payment payment = paymentDAO.getPaymentById(paymentId);

        if (payment == null) {
            request.setAttribute("errorMessage", "Payment not found.");
            request.getRequestDispatcher("/payment/paymentHistory.jsp").forward(request, response);
            return;
        }

        int customerId = user.getId();
        if (customerId <= 0) { // Example check
             session.setAttribute("errorMessage", "User session error. Please login again.");
             response.sendRedirect(request.getContextPath() + "/login.jsp");
             return;
        }
        
        if (payment.getCustomerId() != customerId) { 
            request.setAttribute("errorMessage", "You are not authorized to edit this payment.");
            listPaymentHistory(request, response, user); 
            return;
        }

        // Check if payment status allows editing
        if (!paymentDAO.checkPaymentCanBeModified(paymentId)) {
            request.setAttribute("errorMessage", "This payment cannot be edited as it has been processed or cancelled.");
            // Forward to view page instead of history, so user sees the payment they tried to edit.
            request.setAttribute("payment", payment); // Pass the payment object to viewPayment.jsp
            request.getRequestDispatcher("/payment/viewPayment.jsp").forward(request, response);
            return;
        }

        request.setAttribute("payment", payment);
        request.getRequestDispatcher("/payment/editPayment.jsp").forward(request, response);
    }

    private void processUpdatePayment(HttpServletRequest request, HttpServletResponse response, User user)
            throws SQLException, IOException, ParseException, ServletException {
        HttpSession session = request.getSession();

        String paymentIdStr = request.getParameter("paymentId");
        String orderIdStr = request.getParameter("orderId"); // Usually not changed, but good for context
        String paymentMethod = request.getParameter("paymentMethod");
        String cardHolderName = request.getParameter("cardHolderName");
        String cardNumber = request.getParameter("cardNumber");
        String cardExpiryDate = request.getParameter("cardExpiryDate");
        String cardCVV = request.getParameter("cardCVV"); // Re-entered, handle carefully
        String amountStr = request.getParameter("amount"); // Usually not changed for an existing payment tied to order

        // Basic Validation
        if (paymentIdStr == null || paymentIdStr.trim().isEmpty() ||
            orderIdStr == null || orderIdStr.trim().isEmpty() || /* other fields as in create */
            paymentMethod == null || paymentMethod.trim().isEmpty() ||
            cardHolderName == null || cardHolderName.trim().isEmpty() ||
            cardNumber == null || cardNumber.trim().isEmpty() ||
            cardExpiryDate == null || cardExpiryDate.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty()) {
            // Note: CVV might be optional if card details are not being changed.
            // Adjust validation if CVV is only required when cardNumber changes.

            request.setAttribute("errorMessage", "All fields are required for update.");
            // To repopulate form, you'd fetch the payment by ID and forward
            // For now, simple error and redirect to history or specific edit page with error.
            // showEditPaymentPage(request, response, user); // This would re-fetch and show form
            response.sendRedirect(request.getContextPath() + "/payment?action=edit&paymentId=" + paymentIdStr + "&error=missingFields");
            return;
        }

        int paymentId;
        int orderId;
        double amount;
        try {
            paymentId = Integer.parseInt(paymentIdStr);
            orderId = Integer.parseInt(orderIdStr);
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid Payment ID, Order ID or Amount format.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }
        
        // TODO: Add specific regex validation for card details as in createPayment

        // Fetch existing payment for authorization and to get non-form fields like original paymentDate
        Payment existingPayment = paymentDAO.getPaymentById(paymentId);
        if (existingPayment == null) {
            session.setAttribute("errorMessage", "Payment not found for update.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        // Authorization: Check ownership
        int customerId = user.getId();
        if (existingPayment.getCustomerId() != customerId) {
            session.setAttribute("errorMessage", "You are not authorized to update this payment.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        // Authorization: Check if modifiable status
        if (!paymentDAO.checkPaymentCanBeModified(paymentId)) {
            session.setAttribute("errorMessage", "This payment cannot be updated as it has been processed or cancelled.");
            response.sendRedirect(request.getContextPath() + "/payment?action=view&paymentId=" + paymentId);
            return;
        }

        // Update the payment object
        existingPayment.setPaymentMethod(paymentMethod);
        existingPayment.setCardHolderName(cardHolderName);
        existingPayment.setCardNumber(cardNumber); // Sensitive
        existingPayment.setCardExpiryDate(cardExpiryDate);
        if (cardCVV != null && !cardCVV.trim().isEmpty()) { // Only set CVV if provided
            existingPayment.setCardCVV(cardCVV); // Highly Sensitive
        }
        // Amount and OrderId typically don't change on payment edit unless it's a specific feature.
        // existingPayment.setAmount(amount); // If amount can be changed.
        // existingPayment.setOrderId(orderId); // If order association can change.
        // paymentDate is usually not updated. paymentStatus might change based on business logic not covered here.

        boolean success = paymentDAO.updatePayment(existingPayment);

        if (success) {
            session.setAttribute("successMessage", "Payment details updated successfully for Payment ID: " + paymentId);
            response.sendRedirect(request.getContextPath() + "/payment?action=view&paymentId=" + paymentId);
        } else {
            request.setAttribute("errorMessage", "Failed to update payment details.");
            // Forward back to the edit form with the current (attempted) details
            request.setAttribute("payment", existingPayment); 
            request.getRequestDispatcher("/payment/editPayment.jsp").forward(request, response);
        }
    }

    private void processDeletePayment(HttpServletRequest request, HttpServletResponse response, User user)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String paymentIdStr = request.getParameter("paymentId");

        if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Payment ID is required for deletion.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        int paymentId;
        try {
            paymentId = Integer.parseInt(paymentIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid Payment ID format.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        Payment existingPayment = paymentDAO.getPaymentById(paymentId);
        if (existingPayment == null) {
            session.setAttribute("errorMessage", "Payment not found for deletion.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        // Authorization: Check ownership
        int customerId = user.getId();
        if (existingPayment.getCustomerId() != customerId) {
            session.setAttribute("errorMessage", "You are not authorized to delete this payment.");
            response.sendRedirect(request.getContextPath() + "/payment?action=history");
            return;
        }

        // Authorization: Check if modifiable (and thus deletable) status
        if (!paymentDAO.checkPaymentCanBeModified(paymentId)) {
            session.setAttribute("errorMessage", "This payment cannot be deleted as it has been processed or cancelled.");
            response.sendRedirect(request.getContextPath() + "/payment?action=view&paymentId=" + paymentId);
            return;
        }

        boolean success = paymentDAO.deletePayment(paymentId);

        if (success) {
            session.setAttribute("successMessage", "Payment ID: " + paymentId + " has been successfully deleted.");
        } else {
            session.setAttribute("errorMessage", "Failed to delete Payment ID: " + paymentId + ". It may have already been deleted or an error occurred.");
        }
        response.sendRedirect(request.getContextPath() + "/payment?action=history");
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close(); // Close connection when servlet is destroyed
            }
            if(db != null) {
                // If DBConnector has its own close logic that might close a pool, call it.
                // For this simple DBConnector, conn.close() is sufficient.
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.WARNING, "Failed to close database connection on servlet destroy", e);
        }
        super.destroy();
    }
} 
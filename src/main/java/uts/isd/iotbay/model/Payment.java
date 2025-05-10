package uts.isd.iotbay.model;

import java.io.Serializable;
import java.util.Date;
// Consider using BigDecimal for monetary values for precision
// import java.math.BigDecimal;

public class Payment implements Serializable {

    private int paymentId;
    private int orderId;
    private int customerId;
    private String paymentMethod;
    private String cardHolderName;
    private String cardNumber; // PCI-DSS: Sensitive, consider tokenization or storing only last 4 digits
    private String cardExpiryDate; // Format: "MM/YYYY"
    private String cardCVV; // PCI-DSS: Highly sensitive, should NOT be stored after authorization
    private double amount; // Or BigDecimal amount;
    private Date paymentDate;
    private String paymentStatus; // e.g., "SAVED", "PENDING_SUBMISSION", "SUBMITTED", "FAILED", "CANCELLED"

    // Default constructor
    public Payment() {
    }

    // Parameterized constructor (excluding CVV for safety if not storing)
    public Payment(int paymentId, int orderId, int customerId, String paymentMethod, String cardHolderName,
                   String cardNumber, String cardExpiryDate, double amount, Date paymentDate, String paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        // this.cardCVV = cardCVV; // Explicitly not setting CVV here for safety
    }

    // Getters
    public int getPaymentId() {
        return paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setters
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // toString method for logging/debugging (optional)
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", paymentMethod='" + paymentMethod + "\'" +
                ", cardHolderName='" + cardHolderName + "\'" +
                ", cardNumber='" + "****" + (cardNumber != null && cardNumber.length() > 4 ? cardNumber.substring(cardNumber.length() - 4) : "") + "\'" + // Mask card number
                ", cardExpiryDate='" + cardExpiryDate + "\'" +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + "\'" +
                '}';
    }
} 
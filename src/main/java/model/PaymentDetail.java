
package model;

public class PaymentDetail {
    private final int id;
    private final int userId;
    private String cardHolderName;
    private String cardNumber;
    private String expiryDate;
    private String cardType;
    private boolean isDefault;

    // Constructor
    public PaymentDetail(int id, int userId, String cardHolderName, String cardNumber, String expiryDate, String cardType, boolean isDefault) {
        this.id = id;
        this.userId = userId;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
        this.isDefault = isDefault;
    }

    // Getters and Setters
    public int getPaymentId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "paymentId=" + id +
                ", userId=" + userId +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cardType='" + cardType + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
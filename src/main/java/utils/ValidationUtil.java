package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationUtil {


    
    // 이름 유효성 검사 (영문, 한글, 공백, 하이픈 허용)
    public static String validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        if (name.length() > 30) {
            return fieldName + " must be under 30 characters.";
        }
        if (!name.matches("^[a-zA-Z가-힣\\s'-]{2,30}$")) {
            return fieldName + " contains invalid characters.";
        }
        return null;
    }

    // 전화번호 유효성 검사 (호주 모바일 기준)
    public static String validateAustralianPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Phone number cannot be empty.";
        }
        if (!phone.matches("^(\\+614|04)\\d{8}$")) {
            return "Phone number must be in valid Australian format (e.g., 04XXXXXXXX or +614XXXXXXXX).";
        }
        return null;
    }

    // 이메일 유효성 검사
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty.";
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            return "Invalid email format.";
        }
        return null;
    }

    // 비밀번호 유효성 검사
    public static String validatePasswordChange(String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "New password cannot be empty.";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "New password and confirm password do not match.";
        }
        if (newPassword.length() < 8) {
            return "Password should be at least 8 characters long.";
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }
        if (!newPassword.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter.";
        }
        if (!newPassword.matches(".*\\d.*")) {
            return "Password must contain at least one digit.";
        }
        if (!newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Password must include at least one special character.";
        }
        return null;
    }

    // 호주 우편번호 유효성 검사 (4자리 숫자)
    public static String validateAustralianPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isEmpty()) {
            return "Postal code cannot be empty.";
        }
        if (!postalCode.matches("^\\d{4}$")) {
            return "Postal code must be a 4-digit number.";
        }
        return null;
    }

    // 주소 라인1 유효성 검사
    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Address cannot be empty.";
        }
        if (address.length() > 100) {
            return "Address must be under 100 characters.";
        }
        return null;
    }

    // 생년월일 유효성 검사
    public static String validateBirthDate(String birthDate) {
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return "Birth date cannot be empty.";
        }
        try {
            LocalDate parsed = LocalDate.parse(birthDate);
            if (parsed.isAfter(LocalDate.now())) {
                return "Birth date cannot be in the future.";
            }
        } catch (DateTimeParseException e) {
            return "Invalid date format. Use YYYY-MM-DD.";
        }
        return null;
    }

    // 주문 플랫폼 사용자용 유효성 검사
    public static String validateOrderUserProfile(String fullName, String phone, String postalCode, String addressLine1) {
        String nameError = validateName(fullName, "Full name");
        if (nameError != null) return nameError;

        String phoneError = validateAustralianPhone(phone);
        if (phoneError != null) return phoneError;

        String postalError = validateAustralianPostalCode(postalCode);
        if (postalError != null) return postalError;

        String addressError = validateAddress(addressLine1);
        if (addressError != null) return addressError;

        return null; // All good
    }

    // 기본 프로필 업데이트 유효성 검사
    public static String validateProfileUpdate(String firstName, String lastName, String phone) {
        String firstError = validateName(firstName, "First name");
        if (firstError != null) return firstError;

        String lastError = validateName(lastName, "Last name");
        if (lastError != null) return lastError;

        String phoneError = validateAustralianPhone(phone);
        if (phoneError != null) return phoneError;

        return null;
    }

    public static String validateRegisterUserProfile(
    String firstName, String lastName, String phone, String postalCode, String addressLine1) {

    String firstError = validateName(firstName, "First name");
    if (firstError != null) return firstError;

    String lastError = validateName(lastName, "Last name");
    if (lastError != null) return lastError;

    String phoneError = validateAustralianPhone(phone);
    if (phoneError != null) return phoneError;

    String postalError = validateAustralianPostalCode(postalCode);
    if (postalError != null) return postalError;

    String addressError = validateAddress(addressLine1);
    if (addressError != null) return addressError;

    return null;
}

    // 결제 금액 유효성 검사
    public static String validatePaymentAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return "Amount cannot be empty.";
        }
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                return "Amount must be greater than zero.";
            }
        } catch (NumberFormatException e) {
            return "Amount must be a valid number.";
        }
        return null;
    }

    // 결제 수단 유효성 검사 (예: Credit Card, PayPal, Bank Transfer)
    public static String validatePaymentMethod(String method) {
        if (method == null || method.trim().isEmpty()) {
            return "Payment method cannot be empty.";
        }

        String[] validMethods = {"Credit Card", "PayPal", "Bank Transfer"};
        for (String valid : validMethods) {
            if (valid.equalsIgnoreCase(method.trim())) {
                return null;
            }
        }
        return "Invalid payment method. Valid methods: Credit Card, PayPal, Bank Transfer.";
    }

    // 결제 상태 유효성 검사 (예: Pending, Completed, Failed)
    public static String validatePaymentStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "Payment status cannot be empty.";
        }

        String[] validStatuses = {"Pending", "Completed", "Failed"};
        for (String valid : validStatuses) {
            if (valid.equalsIgnoreCase(status.trim())) {
                return null;
            }
        }
        return "Invalid payment status. Valid statuses: Pending, Completed, Failed.";
    }

    // 전체 결제 정보 유효성 검사
    public static String validatePayment(String amountStr, String method, String status) {
        String amountError = validatePaymentAmount(amountStr);
        if (amountError != null) return amountError;

        String methodError = validatePaymentMethod(method);
        if (methodError != null) return methodError;

        String statusError = validatePaymentStatus(status);
        if (statusError != null) return statusError;

        return null; // All good
    }


}

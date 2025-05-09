package utils;

//===================Registration Validation Utility===================//
// This class contains utility methods for validating user input in various scenarios.
// It includes methods for validating user profile updates, password changes, and email formats.
// The methods return error messages if the input is invalid, or null if the input is valid.
//=======================================================================//
public class ValidationUtil {

    // Validates user profile update (e.g., first name, last name, phone)
    public static String validateProfileUpdate(String firstName, String lastName, String phone) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return "First name cannot be empty.";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Last name cannot be empty.";
        }
        if (phone == null || phone.trim().isEmpty()) {
            return "Phone number cannot be empty.";
        }
        return null; // No error
    }

    // Validates password change (new password and confirmation match)
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
        return null; // No error
    }

    // Validates if an email is in the correct format (basic email validation)
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty.";
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            return "Invalid email format.";
        }
        return null; // No error
    }

    
}

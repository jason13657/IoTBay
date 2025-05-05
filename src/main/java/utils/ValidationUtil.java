package utils;

public class ValidationUtil {
    public static String validateProfileUpdate(String firstName, String lastName, String phone) {
        if (firstName == null || firstName.isEmpty()) return "이름을 입력하세요.";
        if (lastName == null || lastName.isEmpty()) return "성을 입력하세요.";
        if (phone == null || phone.isEmpty()) return "전화번호를 입력하세요.";
        return null;
    }

    public static String validatePasswordChange(String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.isEmpty()) return "새 비밀번호를 입력하세요.";
        if (!newPassword.equals(confirmPassword)) return "비밀번호가 일치하지 않습니다.";
        return null;
    }

    // 회원가입 검증 메서드 추가
    public static String validateRegistration(String fullName, String email, String password, String confirmPassword, String phone) {
        if (fullName == null || fullName.isEmpty()) return "이름을 입력하세요.";
        if (email == null || email.isEmpty()) return "이메일을 입력하세요.";
        if (password == null || password.isEmpty()) return "비밀번호를 입력하세요.";
        if (!password.equals(confirmPassword)) return "비밀번호가 일치하지 않습니다.";
        if (phone == null || phone.isEmpty()) return "전화번호를 입력하세요.";
        return null;
    }
}

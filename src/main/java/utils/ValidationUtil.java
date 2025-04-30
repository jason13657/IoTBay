package utils;

public class ValidationUtil {
    public static String validateRegistration(String fullName, String email, String password, String confirmPassword, String phone) {
        if (fullName == null || fullName.isEmpty()) return "이름을 입력하세요.";
        if (email == null || !email.contains("@")) return "유효한 이메일을 입력하세요.";
        if (!password.equals(confirmPassword)) return "비밀번호가 일치하지 않습니다.";
        if (phone == null || phone.isEmpty()) return "전화번호를 입력하세요.";
        return null;
    }
}

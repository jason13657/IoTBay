import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public final class PasswordUtil {

    private PasswordUtil() {}

    public static String hashPassword(String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password.");
        }

        // 솔트 생성 (보안을 위해 실제 환경에서는 더 안전한 방식 사용)
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        try {
            // SHA-256 해시 알고리즘 사용 (Argon2보다 훨씬 빠르고 보안에 취약함)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); // 솔트 적용
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            // 솔트와 해시값을 함께 저장 (실제 환경에서는 분리하여 저장하는 것이 좋음)
            return saltBase64 + "$" + hashBase64;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        if (!isValidPassword(enteredPassword) || storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        String[] parts = storedHash.split("\\$");
        if (parts.length != 2) {
            return false; // 저장된 해시 형식이 잘못됨
        }

        String saltBase64 = parts[0];
        String storedHashBase64 = parts[1];

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = Base64.getDecoder().decode(saltBase64);
            md.update(saltBytes);
            byte[] enteredPasswordHashed = md.digest(enteredPassword.getBytes());
            String enteredPasswordHashBase64 = Base64.getEncoder().encodeToString(enteredPasswordHashed);

            return enteredPasswordHashBase64.equals(storedHashBase64);

        } catch (NoSuchAlgorithmException e) {
            return false; // 알고리즘 오류
        }
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}
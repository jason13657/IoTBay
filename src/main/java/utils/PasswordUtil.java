package utils;

import java.security.MessageDigest;

public class PasswordUtil {
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static boolean verifyPassword(String password, String hashedPassword) throws Exception {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }
}

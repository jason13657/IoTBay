package utils;

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

        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); 
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

           
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
            return false;
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
            return false; 
        }
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}
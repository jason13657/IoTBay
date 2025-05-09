package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Method to hash the password
    public static String hashPassword(String password) {
        // Generate a salt and hash the password
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // Method to verify if the entered password matches the hashed password
    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(enteredPassword, storedHash);
    }
}

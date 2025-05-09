// build.gradle에 추가
// implementation 'de.mkammerer:argon2-jvm:2.11'

import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2;

public final class PasswordUtil {
    private static final Argon2 argon2 = Argon2Factory.create();

    private PasswordUtil() {}

    public static String hashPassword(String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password.");
        }
        // 파라미터: iterations, memory, parallelism
        return argon2.hash(3, 65536, 1, password.toCharArray());
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        if (!isValidPassword(enteredPassword) || storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        return argon2.verify(storedHash, enteredPassword.toCharArray());
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}

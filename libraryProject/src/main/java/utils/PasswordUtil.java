package utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Метод для хеширования пароля
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    // Метод для проверки соответствия введенного пароля и хешированного
    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}

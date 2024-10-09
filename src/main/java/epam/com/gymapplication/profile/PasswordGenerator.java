package epam.com.gymapplication.profile;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class PasswordGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?";
    private static final String ALL_CHARACTERS = LETTERS + DIGITS + SPECIAL_CHARACTERS;
    private static final int passwordLength = 10;

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            password.append(ALL_CHARACTERS.charAt(new SecureRandom().nextInt(ALL_CHARACTERS.length())));
        }

        return shufflePassword(String.valueOf(password));
    }

    private String shufflePassword(String password) {
        StringBuilder shuffledPassToStringBuilder = new StringBuilder();
        List<String> shuffledPassword = new ArrayList<>(List.of(password));
        Collections.shuffle(shuffledPassword);
        for (String s : shuffledPassword) {
            shuffledPassToStringBuilder.append(s);
        }

        return String.valueOf(shuffledPassToStringBuilder);

    }
}

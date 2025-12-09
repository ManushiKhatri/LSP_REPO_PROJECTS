package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Generates alphanumeric passwords using SecureRandom.
 */
public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {

    private static final String ALLOWED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ALLOWED.length());
            sb.append(ALLOWED.charAt(index));
        }
        return sb.toString();
    }
}

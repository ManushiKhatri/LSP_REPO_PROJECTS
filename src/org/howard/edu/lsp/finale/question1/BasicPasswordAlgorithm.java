package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Generates digit-only passwords using java.util.Random.
 */
public class BasicPasswordAlgorithm implements PasswordAlgorithm {

    private static final String DIGITS = "0123456789";
    private final Random random = new Random();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(index));
        }
        return sb.toString();
    }
}

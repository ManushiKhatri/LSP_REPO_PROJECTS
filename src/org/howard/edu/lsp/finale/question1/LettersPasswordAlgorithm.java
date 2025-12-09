package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Generates letter-only passwords.
 */
public class LettersPasswordAlgorithm implements PasswordAlgorithm {

    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final Random random = new Random();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }
        return sb.toString();
    }
}

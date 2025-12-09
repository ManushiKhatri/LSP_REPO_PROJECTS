package org.howard.edu.lsp.finale.question1;

/*
 * ===========================
 * DESIGN PATTERN DOCUMENTATION
 * ===========================
 *
 * Patterns Used:
 *
 * 1. Singleton Pattern
 *    - This pattern ensures that only one instance of
 *      PasswordGeneratorService exists throughout the application.
 *    - It is implemented using a private static instance variable
 *      and a public static getInstance() method.
 *    - This is appropriate because password generation should be
 *      centrally managed to avoid inconsistent behavior and duplicate
 *      service instances.
 *
 * 2. Strategy Pattern
 *    - The Strategy Pattern is used to support multiple interchangeable
 *      password-generation behaviors.
 *    - The PasswordAlgorithm interface defines a common contract.
 *    - Concrete implementations include:
 *         • BasicPasswordAlgorithm
 *         • EnhancedPasswordAlgorithm
 *         • LettersPasswordAlgorithm
 *    - The active algorithm can be changed at runtime using setAlgorithm()
 *      without modifying the service or client code.
 *
 * Why These Patterns Are Appropriate:
 *
 * The Singleton Pattern provides controlled, global access to a single
 * shared password generator, which prevents unnecessary duplication
 * and maintains consistent behavior across the system.
 *
 * The Strategy Pattern enables flexible and extensible password generation.
 * New algorithms can be added easily without impacting existing logic,
 * making the design scalable, maintainable, and aligned with best
 * object-oriented practices.
 */


public class PasswordGeneratorService {

    private static PasswordGeneratorService instance;
    private PasswordAlgorithm currentAlgorithm;

    private PasswordGeneratorService() {}

    public static synchronized PasswordGeneratorService getInstance() {
        if (instance == null) {
            instance = new PasswordGeneratorService();
        }
        return instance;
    }

    public void setAlgorithm(String name) {
        switch (name.toLowerCase()) {
            case "basic":
                currentAlgorithm = new BasicPasswordAlgorithm();
                break;
            case "enhanced":
                currentAlgorithm = new EnhancedPasswordAlgorithm();
                break;
            case "letters":
                currentAlgorithm = new LettersPasswordAlgorithm();
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm name.");
        }
    }

    public String generatePassword(int length) {
        if (currentAlgorithm == null) {
            throw new IllegalStateException("Algorithm not selected.");
        }
        return currentAlgorithm.generate(length);
    }

    // Testing helper (package-private)
    void reset() {
        currentAlgorithm = null;
    }
}

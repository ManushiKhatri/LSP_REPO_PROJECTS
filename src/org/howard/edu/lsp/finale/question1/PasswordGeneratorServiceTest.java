package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
        service.reset();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull(service);
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second =
                PasswordGeneratorService.getInstance();
        assertSame(service, second);
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            service.generatePassword(10);
        });
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);
        assertEquals(10, p.length());

        for (char c : p.toCharArray()) {
            assertTrue(Character.isDigit(c));
        }
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);
        assertEquals(12, p.length());

        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c));
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);
        assertEquals(8, p.length());

        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetter(c));
        }
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);

        assertTrue(p1.matches("\\d+"));
        assertTrue(p2.matches("[A-Za-z]+"));
        assertTrue(p3.matches("[A-Za-z0-9]+"));
    }
}

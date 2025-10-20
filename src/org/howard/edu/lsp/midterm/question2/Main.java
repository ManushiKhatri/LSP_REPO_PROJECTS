package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
        System.out.println("Circle radius 3.0 \u2192 area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 \u2192 area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 \u2192 area = " + AreaCalculator.area(4));

        // Exception demo
        try {
            AreaCalculator.area(0.0); // invalid radius
            System.out.println("This line should not print.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
     Overloading provides one cohesive API name ("area") for conceptually identical
     behavior with different parameter types/shapes. It improves readability and discoverability,
     while the compiler resolves the correct method by signature. Separate names (circleArea, etc.)
     are workable but noisier and harder to maintain at scale.
    */
}

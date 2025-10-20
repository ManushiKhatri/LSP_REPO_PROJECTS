package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator {
    // Circle
    public static double area(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("radius must be > 0");
        return Math.PI * radius * radius;
    }

    // Rectangle
    public static double area(double width, double height) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("dimensions must be > 0");
        return width * height;
    }

    // Triangle (base & height)
    public static double area(int base, int height) {
        if (base <= 0 || height <= 0) throw new IllegalArgumentException("dimensions must be > 0");
        return 0.5 * base * height;
    }

    // Square
    public static double area(int side) {
        if (side <= 0) throw new IllegalArgumentException("side must be > 0");
        return side * side;
    }
}

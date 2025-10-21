# References – Question 2 (AreaCalculator)

**Name:** [Manushi Khatri]  
**Course:** Large Scale Programming (Fall 2025)  
** Midterm Programming – Question 2 **

---

### Primary References
1. Oracle Java SE Documentation – [Math Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html)  
2. Oracle Java Tutorials – [Defining Methods](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)  
3. GeeksforGeeks – [Method Overloading in Java](https://www.geeksforgeeks.org/overloading-in-java/)  
4. W3Schools – [Java Exceptions (IllegalArgumentException)](https://www.w3schools.com/java/java_try_catch.asp)

---

### AI / Internet Source Disclosure
- **Tool used:** ChatGPT (GPT-5, OpenAI, October 2025)  
- **Purpose:** Used to verify correct method signatures and formatting for overloaded `area()` methods.  
- **Prompt:** *"How can I implement a Java program with multiple overloaded methods that calculate areas of different shapes while validating for negative or zero dimensions?"*
- **AI Answer (Excerpt):** 
'''
Method overloading means you can define multiple methods with the same name but different parameter lists (different number or type of parameters).
This makes your code clean and easy to read — for example, one area() method can handle multiple shapes.
Java Program Example:
public class ShapeAreaCalculator {

    // Overloaded method for rectangle area
    public static double area(double length, double width) {
        if (length <= 0 || width <= 0) {
            System.out.println("Error: Rectangle dimensions must be positive.");
            return 0;
        }
        return length * width;
    }

    // Overloaded method for circle area
    public static double area(double radius) {
        if (radius <= 0) {
            System.out.println("Error: Circle radius must be positive.");
            return 0;
        }
        return Math.PI * radius * radius;
    }

    // Overloaded method for triangle area
    public static double area(double base, double height, boolean isTriangle) {
        if (base <= 0 || height <= 0) {
            System.out.println("Error: Triangle base and height must be positive.");
            return 0;
        }
        return 0.5 * base * height;
    }

    // Main method to test all shapes
    public static void main(String[] args) {
        System.out.println("Rectangle area: " + area(5, 3));
        System.out.println("Circle area: " + area(4));
        System.out.println("Triangle area: " + area(6, 2, true));

    // Try invalid inputs
        System.out.println("\nTesting invalid values:");
        System.out.println("Rectangle area: " + area(-5, 3));
        System.out.println("Circle area: " + area(0));
    }
}

How it Works:

The area() method is overloaded three times — each version has a different parameter list.

Before calculating, each method checks if the given dimensions are positive.

If invalid values (negative or zero) are passed, an error message is printed and 0 is returned.

Why This is Good Practice:

Encapsulation of logic: All area logic lives in one class.

Code readability: Overloading keeps the same intuitive method name (area).
'''

- **Adaption:** I adapted this structure and added my own exception handling and print formatting in Eclipse.

Validation ensures your program avoids incorrect results and unexpected crashes.
---



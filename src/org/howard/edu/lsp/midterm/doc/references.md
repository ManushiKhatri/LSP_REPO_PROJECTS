# References – Essay and Rationale Questions (Q1, Q2, Q3, Q4 Rationale, Q5)

**Name:** Manushi Khatri  
**Course:** Large Scale Programming (Fall 2025)  
**Midterm – Essay Section**

---

### Primary References
1. Riel, A. J. (1996). *Object-Oriented Design Heuristics*. Addison-Wesley.  
2. Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley.  
3. Oracle Java Tutorials – [Classes and Objects](https://docs.oracle.com/javase/tutorial/java/concepts/class.html)  
4. Oracle Java Tutorials – [Interfaces and Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)  

---

### AI / Internet Source Disclosure
- **Tool used:** ChatGPT (GPT-5, OpenAI, October 2025)  
- **Prompt 1:**  
  *“Explain in simple language how class cohesion and single responsibility principles improve code maintainability, and give an example of refactoring a class that tries to do too much.”* 
- **Purpose:** To clarify conceptual reasoning about cohesion, abstraction, and composition while preparing short, well-structured essay answers.  
- **AI Answer (Excerpt):** 
```
Class cohesion means that everything inside a class should work together toward one clear purpose. The single responsibility principle (SRP) builds on that idea — each class should have only one job or reason to change. When a class focuses on one thing, the code becomes easier to understand, test, and update without breaking other parts of the program.

How it improves maintainability:

- Easier to debug: You know exactly where to look when something goes wrong.

- Simpler to reuse: Small, focused classes can be used in other parts of the program.

- Safer to change: You can modify one class without worrying about unintended side effects in unrelated features.
```
- **Adaptation:** I used this explanation to shape my Q1 discussion of the `StudentPortalHelper` class and Riel’s cohesion heuristic.

- **Prompt 2:** "Which is better practice in Java — method overloading or creating multiple methods with different names (like rectangleArea, circleArea, etc.)?" 
- **Purpose:** To understand when method overloading provides cleaner and more consistent code design compared to defining multiple functions with different names.  
- **AI Answer (Excerpt):**
```
Method Overloading — Best for Same Concept, Different Inputs

Use method overloading when all methods represent the same conceptual operation, but differ in parameter type or count.

Example:

class ShapeCalculator {
    double area(Rectangle r) { return r.getWidth() * r.getHeight(); }
    double area(Circle c)    { return Math.PI * c.getRadius() * c.getRadius(); }
    double area(Triangle t)  { return 0.5 * t.getBase() * t.getHeight(); }
}


Here:

All methods mean "area".

You let the compiler pick the right method based on argument type.

This keeps your API cohesive and semantic (one idea, one name).

So client code stays consistent:

ShapeCalculator calc = new ShapeCalculator();
System.out.println(calc.area(rect));
System.out.println(calc.area(circle));

🚫 Different Method Names — Use Only When Behavior Is Conceptually Different

If the methods aren’t really doing the same logical task, don’t overload—give them separate, descriptive names.

Example:

double computeArea(Shape s)   // logical area
double computePerimeter(Shape s) // different concept entirely


Or, if overloading would cause confusion:

// Bad: both return int but mean different things
int get(String key);
int get(int index);

// Better:
int getByKey(String key);
int getByIndex(int index);

⚖️ Design Trade-off Summary
Criterion	Method Overloading	Separate Named Methods
Conceptual Meaning	Same operation (e.g., area)	Different operations (e.g., area, perimeter)
Clarity	Cleaner and polymorphic	More explicit
Maintainability	Easier for similar operations	Better when signatures could confuse users
Runtime behavior	Resolved at compile time	Each call is explicit
🚀 Best Practice in Java

✅ Use method overloading for conceptually identical behaviors that only differ in parameters (type or number).

🧭 Use distinct names when the actions differ in semantics or could confuse users.

⚠️ Avoid excessive overloading — more than 3–4 variants often harms readability.

In your “area” example, overloading is the better practice:

area(Rectangle r)
area(Circle c)
area(Triangle t)


because each method represents the same conceptual action — computing area — just for a different shape type.
```
- **Adaptation:** I summarized this reasoning as a short explanatory comment in my `Main` class, stating that overloading provides clarity and consistency while reducing redundant method names.


- **Prompt 3:** "Why can’t a subclass-based trim system change trim levels at runtime, and how can composition solve that?"  
- **Purpose:** To confirm my understanding of inheritance vs. composition and how composition can make an object’s state or behavior dynamically changeable in Java.
- **AI Answer (Excerpt):**
```
Great question. Think “car trims” like Base, Sport, Luxury.

Why inheritance (subclasses) can’t swap trims at runtime

If you model trims as subclasses…

class Car {}
class BaseCar extends Car {}
class SportCar extends Car {}
class LuxuryCar extends Car {}


…the trim is baked into the type at construction. In most OO languages:

The dynamic behavior (methods/fields) comes from the object’s class chosen at new.

You can’t turn a BaseCar into a SportCar without constructing a new object and copying state. “Changing class” would break the object’s invariants and the type system.

Downcasts don’t help—you’re still the same instance/class; they only change how you view it, not what it is.

Any code that expects SportCar-specific fields/methods relies on that type existing from birth.

So with subclassing, “trim” is a compile-time classification, not a runtime choice.

How composition fixes it (Strategy/State/Decorator)

Make “trim” be data/behavior you hold, not what you are.

// Behavior surface for a trim package
interface Trim {
    int horsepower();
    Suspension suspension();
    Money priceDelta();
    void applySoftLimits(CarSensors s, CarActuators a);
}

// Concrete trim behaviors
class BaseTrim implements Trim { /* ... */ }
class SportTrim implements Trim { /* ... */ }
class LuxuryTrim implements Trim { /* ... */ }

// The car *has a* Trim (composition) instead of *is a* Trim (inheritance)
class Car {
    private Trim trim;
    private final Drivetrain drivetrain;
    private final Infotainment ui;
    // ... other core state that should persist across trim changes

    Car(Trim initialTrim, Drivetrain d, Infotainment ui) {
        this.trim = initialTrim;
        this.drivetrain = d;
        this.ui = ui;
    }

    // Swap at runtime
    public void setTrim(Trim newTrim) { this.trim = newTrim; }

    // Delegate behavior that varies by trim
    public int horsepower()         { return trim.horsepower(); }
    public Suspension suspension()  { return trim.suspension(); }
    public Money price()            { return basePrice().plus(trim.priceDelta()); }

    public void controlLoop(CarSensors s, CarActuators a) {
        trim.applySoftLimits(s, a); // varies by trim
        // ... rest of control logic
    }
}


Now you can do:

Car car = new Car(new BaseTrim(), d, ui);
car.setTrim(new SportTrim());  // runtime swap — no object replacement

Variations you can use

Strategy pattern: Trim is the strategy; Car delegates to it (as above).

State pattern: If trim transitions drive different allowed actions, encode them as states with explicit transitions.

Decorator / feature modules: Build a trim by stacking feature components:

interface Feature { void apply(Car c); }
class SportSuspension implements Feature { /* ... */ }
class BigBrakes implements Feature { /* ... */ }
// Car holds List<Feature> and you can add/remove features on the fly.


This keeps the stable identity and state of the car while swapping behavioral modules. It respects “composition over inheritance,” supports runtime changes, and keeps you open to new trims without subclass explosions.
```
- **Adaptation:** I applied this explanation in my essay answer to show how replacing inheritance with composition allows runtime flexibility and cleaner code design.

- **Prompt 4:** "How do abstract classes and interfaces work together in the Smart-Campus Device design, and why is this approach not true multiple inheritance?"  
- **Purpose:** To ensure that my understanding of abstraction, polymorphism, and Java’s interface system correctly supports the reasoning in my design explanation.
- **AI Answer (Excerpt):**
```
Here’s the clean split:

Roles

Abstract class (Device): one shared base with core state and partial behavior common to all campus devices.

Holds fields (id, location, firmwareVersion, powerState).

Enforces invariants/flows (e.g., template methods for boot() → selfTest() → online()).

Provides shared utilities (telemetry batching, retry backoff).

Interfaces (capabilities): orthogonal “can-do” contracts you can mix into any device:

Connectable (join/leave network), Schedulable (run at times), Sensor<T> (read data), Actuator (perform action), Updatable (OTA), PowerManaged (sleep/wake), etc.

A concrete device extends one abstract class and implements many interfaces:

abstract class Device {
    protected final String id;
    protected String location;
    protected PowerState power = PowerState.SLEEP;

    protected Device(String id, String location) { this.id = id; this.location = location; }

    // Template method with invariant steps
    public final void boot() {
        initHardware();
        selfTest();
        goOnline();
    }
    protected abstract void initHardware();
    protected void selfTest() { /* default */ }
    protected void goOnline() { /* default */ }
}

interface Connectable { void connect(); void disconnect(); }
interface Sensor<T> { T read(); }
interface Updatable { void checkForUpdate(); void applyUpdate(byte[] img); }

final class AirQualityNode extends Device
        implements Connectable, Sensor<AirSample>, Updatable {

    AirQualityNode(String id, String loc) { super(id, loc); }

    @Override protected void initHardware() { /* sensors, radio */ }
    @Override public void connect() { /* Wi-Fi/LoRa join */ }
    @Override public void disconnect() { /* … */ }
    @Override public AirSample read() { /* sample PM2.5, CO₂, VOC */ }
    @Override public void checkForUpdate() { /* … */ }
    @Override public void applyUpdate(byte[] img) { /* … */ }
}

How they work together

The abstract class supplies implementation + shared state + lifecycle rules.

The interfaces declare optional capabilities; the device class promises to implement them.

Call sites program to capabilities: e.g., a scheduler only needs Schedulable, a dashboard only needs Sensor<?>.

Why this is not true multiple inheritance

You get single inheritance of implementation (only one superclass: Device).

Interfaces provide no instance state/constructors (and their default methods don’t carry per-object fields), so they don’t contribute stored behavior like a second parent class would.

No “diamond of death” for fields: there’s only one Device state; interface method conflicts are resolved by simple rules (Class > Interface default, or explicitly override).

Memory/layout and constructor chaining come from the single superclass only—unlike C++ multiple inheritance, where two concrete parents can each carry their own data and constructors.

Why this design is nice for Smart-Campus

Keeps a common lifecycle (safety + invariants) in Device.

Lets you mix capabilities freely (sensor + actuator + OTA) without subclass explosion.

Plays great with composition: each interface’s implementation can delegate to swappable strategy objects (e.g., ConnectivityStrategy, UpdatePolicy) when you need runtime variability.
```
- **Adaptation:** I used this understanding to write my rationale explaining why the design uses abstraction for common structure and interfaces for flexible optional features.


---

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
- **Purpose:** To clarify conceptual reasoning about cohesion, abstraction, and composition while preparing short, well-structured essay answers. 
- **Prompt:**  
  *“Explain in simple language how class cohesion and single responsibility principles improve code maintainability, and give an example of refactoring a class that tries to do too much.”*  
- **AI Answer (Excerpt):** 
```
Class cohesion means that everything inside a class should work together toward one clear purpose. The single responsibility principle (SRP) builds on that idea — each class should have only one job or reason to change. When a class focuses on one thing, the code becomes easier to understand, test, and update without breaking other parts of the program.

How it improves maintainability:

- Easier to debug: You know exactly where to look when something goes wrong.

- Simpler to reuse: Small, focused classes can be used in other parts of the program.

- Safer to change: You can modify one class without worrying about unintended side effects in unrelated features.
```
- **Adaptation:** I used this explanation to shape my Q1 discussion of the `StudentPortalHelper` class and Riel’s cohesion heuristic.

---

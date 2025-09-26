This repository contains my coursework for Large Scale Programming.

- **Assignment 1** – A minimal Java program that prints `Hello World!!`.  
  Package: `org.howard.edu.lsp.assignment1`, file: `HelloWorld.java`.

- **Assignment 2** – A CSV ETL pipeline in Java.  
  Reads `data/products.csv`, transforms rows (uppercase name, 10% discount for Electronics, recategorize to *Premium Electronics* if discounted price > 500, add `PriceRange`), and writes `data/transformed_products.csv`. Uses only **relative paths**.
  Package: 'org.howard.edu.lsp.assignment2', file: 'ETLPipeline.java'.
  
- **Assignment 3** – Object-Oriented ETL with AI
	Refactors A2 into small, testable classes with the same input/output and behavior:

	- Data model: Product (immutable), PriceRange (enum)
	- Extract: ProductReader → CsvProductReader
	- Transform: ProductTransformer → DefaultProductTransformer
	- Load: ProductWriter → CsvProductWriter
	- Orchestration: ETLPipelineMain (runs Extract → Transform → Load and prints the same summary)
	- Also includes short documentation:
		- doc/REFLECTION_A3.md (design choices & testing to prove A3 == A2)
		- doc/AI_PROMPTS_A3.md (prompts + brief excerpts; academic integrity notes)
	- Package: org.howard.edu.lsp.assignment3.
  
---

## How to Run

The Programs are designed for use with Eclipse IDE, but runs from command line as well.
Requirements:
- Git
- Java JDK 17+ (Java 21 recommended)

### 1) Clone the repository
### 2) Run in Eclipse
	- Open Eclipse -> File -> Import -> Existing Projects into Workspace
	- Root Directory: select the cloned folder, finish.
	- Select intended file: Run As -> Java Application.


## Project Structure
```
data/
├── products.csv
└── transformed_products.csv
src/
└── org/howard/edu/lsp/assignment1/
│   └── HelloWorld.java
└── org/howard/edu/lsp/assignment2/
│   └── ETLPipeline.java
└── org/howard/edu/lsp/assignment3/
    ├── Product.java
    ├── PriceRange.java
    ├── ProductReader.java
    ├── CsvProductReader.java
    ├── ProductTransformer.java
    ├── DefaultProductTransformer.java
    ├── ProductWriter.java
    ├── CsvProductWriter.java
    ├── ETLPipelineMain.java
    └── doc/
        ├── REFLECTION_A3.md
        └── AI_PROMPTS_A3.md
README.md
.gitignore

```

## External Source Usage Disclosure

**Assignment 1**
N/A

**Assignment 2**
1. Link: https://stackoverflow.com/questions/24923179/parse-a-semicolon-separated-file-in-java
	- Usage: Confirmed approaches for parsing semicolon-separated CSV exported by Excel/regional settings. 
	- Adaptation: Ensured my parser accepts ; as a delimiter and verified behavior with semicolon CSVs. 

2. Link: https://www.baeldung.com/java-round-decimal-number 
	- Usage: Reviewed correct rounding techniques in Java and why BigDecimal is preferred for currency.
	- Adaptation: Applied BigDecimal.setScale(2, RoundingMode.HALF_UP) after applying discounts to guarantee two-decimal prices.

3. Link: https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
	- Usage: Revisited Java NIO Path/Paths.get() usage and relative path handling. 
	- Adaptation: Used relative paths like Paths.get("data","products.csv") and printed outputPath.toAbsolutePath() in the run summary for clarity.

**Assignment 3**
1. A3 is a design refactor of A2 (same I/O & behavior). Any generic phrasing for Javadocs and class decomposition ideas was assisted by a generative AI tool. I reviewed, edited, and tested all code myself to ensure correctness and to maintain identical outputs to A2.


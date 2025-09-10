This repository contains my coursework for Large Scale Programming.

- **Assignment 1** – A minimal Java program that prints `Hello World!!`.  
  Package: `org.howard.edu.lsp.assignment1`, file: `HelloWorld.java`.

- **Assignment 2** – A CSV ETL pipeline in Java.  
  Reads `data/products.csv`, transforms rows (uppercase name, 10% discount for Electronics, recategorize to *Premium Electronics* if discounted price > 500, add `PriceRange`), and writes `data/transformed_products.csv`. Uses only **relative paths**.
  Package: 'org.howard.edu.lsp.assignment2', file: 'ETLPipeline.java'.
  
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

data/
├── products.csv
└── transformed_products.csv
src/
└── org/howard/edu/lsp/assignment1/
	└── HelloWorld.java
└── org/howard/edu/lsp/assignment2/
	└── ETLPipeline.java
README.md
.gitignore

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

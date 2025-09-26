## REFLECTION
Object-Oriented Redesign of My ETL Pipeline (A2 → A3)

## 1) What changed from A2 to A3 (design & structure)

**Assignment 2 (A2):** One monolithic class `ETLPipeline` (single `main`) that handled file I/O, parsing, transformation rules, and CSV writing. While functional, responsibilities were mixed together, making it harder to swap components or test them in isolation.

**Assignment 3 (A3):** I decomposed the program into focused classes and small interfaces:

- **Data model**: `Product` (immutable value object) and `PriceRange` enum.
- **Extraction**: `ProductReader` interface + `CsvProductReader` implementation (tolerates `,`, `;`, or TAB).
- **Transformation**: `ProductTransformer` interface + `DefaultProductTransformer` (same rules as A2).
- **Load**: `ProductWriter` interface + `CsvProductWriter` (writes headers and rows).
- **Orchestration**: `ETLPipelineMain` wires the above (Extract → Transform → Load) and prints the same run summary as A2.

**Before vs After (at a glance)**

| Concern            | A2 (Monolith)                          | A3 (OO)                                                                 |
|--------------------|-----------------------------------------|-------------------------------------------------------------------------|
| Data               | Inline fields in one class              | `Product` (immutable), `PriceRange` enum                                |
| Extract            | `BufferedReader` in `main`              | `ProductReader` → `CsvProductReader`                                    |
| Transform          | Inline methods in `main`                | `ProductTransformer` → `DefaultProductTransformer`                       |
| Load               | `writeOutput` in `main`                 | `ProductWriter` → `CsvProductWriter`                                     |
| Composition        | Hard-wired inside `main`                | `ETLPipelineMain` composes interfaces (swappable)                        |
| Testability        | Coarse-grained                          | Unit-testable in parts; easy to stub readers/writers/transformers        |
| Extensibility      | Edit monolith                           | Add a new reader/writer/transformer without touching others              |

This separation reduces coupling, improves cohesion, and makes it simpler to change one part (e.g., switch to a database reader) without touching the rest.

---

## 2) How A3 is “more object-oriented”

- **Objects & Classes:** Real-world entities are modeled as objects (`Product`) and cohesive classes (`CsvProductReader`, `DefaultProductTransformer`, `CsvProductWriter`).
- **Encapsulation:** Parsing, transforming, and writing logic live behind interfaces; internal details are hidden and accessed via public methods.
- **Polymorphism:** `ProductReader`, `ProductWriter`, and `ProductTransformer` define interchangeable roles. I can swap `CsvProductReader` with another implementation (e.g., `JsonProductReader`) without changing `ETLPipelineMain`.
- **(Optional) Inheritance:** Not essential here; composition + interfaces were a better fit. If needed later, specialized writers or readers could subclass a common base (e.g., shared CSV utilities).
- **Single Responsibility Principle (SRP):** Each class has a single reason to change (e.g., only `DefaultProductTransformer` would change if the discount logic changes).

---

## 3) What stayed the same (to preserve correctness)

Per the assignment requirements, A3 preserves **everything observable from A2**:

- **Inputs/outputs:** Reads `data/products.csv`; writes `data/transformed_products.csv` using the same relative paths.
- **Transform rules (identical to A2):**
  1) Uppercase product name.  
  2) If category is “Electronics”, apply **10% discount** and round **HALF_UP** to 2 decimals.  
  3) If (discounted) price > 500 **and** original category was Electronics → recategorize to **“Premium Electronics”**.  
  4) Compute **PriceRange**: `≤10 → Low`, `≤100 → Medium`, `≤500 → High`, else `Premium`.  
- **Robust parsing:** Accept commas, semicolons, or TABs between fields; allow `$` in the Price field.  
- **Error handling:** Skip malformed rows; print a run summary with **Rows read / transformed / skipped**, and handle **missing** and **empty** input files gracefully.

---

## 4) Why certain design choices were made

- **Interfaces for I/O & transforms:** This decouples orchestration from concrete I/O. In future, I can add `DbProductReader` or `ParquetProductWriter` without touching transformation logic.
- **Immutable `Product`:** Using final fields reduces accidental mutation across steps; safer when transformations become more complex.
- **`BigDecimal` with `RoundingMode.HALF_UP`:** Preserves currency precision exactly as in A2; identical results ensure output equivalence.
- **Enum `PriceRange`:** Encodes the valid set of ranges and avoids typos (“Premium” vs “Premuim”).

---

## 5) Testing strategy to prove A3 == A2

I validated A3 against A2 using the same input files and by inspecting both the console summary and the output CSV:

**Test cases**
1. **Happy path (mixed delimiters):** CSV with commas, semicolons, and TABs; verifies tolerant parsing, discount, rounding, recategorization, and PriceRange.
2. **Dollar sign in Price:** Inputs like `$9.99`; verifies stripping and numeric parse.
3. **Malformed rows:** Too few columns or non-numeric ProductID/Price; rows are skipped.
4. **Empty file (only header):** Produces a header-only output; summary reports 0 rows read/transformed.
5. **Missing input file:** Program prints a helpful error and does not crash.
6. **Boundary price**

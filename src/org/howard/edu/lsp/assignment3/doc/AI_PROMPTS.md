## How I used an AI assistant (overview)
- **Goal:** Brainstorm class decomposition, align with OO goals, and speed up boilerplate (interfaces/Javadocs).
- **My role:** I decided the architecture, wrote/edited the code, and verified behavior against A2 outputs.
- **Caveat:** I did not paste long AI code outputs verbatim; I adapted/edited and tested each change.

> The assignment requires *several prompts plus short excerpts*, not full transcripts. Below are representative snippets and what I used/changed.

---

### Prompt 1 — “Decompose my A2 ETL into OO classes while keeping I/O identical”
**My prompt (excerpt):**  
> “I have a single-file ETL in Java that reads `data/products.csv`, applies discount rules to Electronics, uppercases names, computes a price range, and writes `data/transformed_products.csv`. Propose an OO decomposition that keeps the same input/output and error handling. Use interfaces so I can swap components.”

**AI response (short excerpt):**  
> “Create `Product`, `ProductReader` (with `CsvProductReader`), `ProductTransformer` (with `DefaultProductTransformer`), and `ProductWriter` (with `CsvProductWriter`). Add an `ETLPipelineMain` that composes these. Keep relative paths and reuse `BigDecimal` with `HALF_UP`.”

**What I accepted/modified:**  
- Accepted the 5-class structure and interface approach.  
- I chose to keep `Product` immutable and added a `PriceRange` enum for clarity.

---

### Prompt 2 — “Write a `DefaultProductTransformer` that matches my A2 rules exactly”
**My prompt (excerpt):**  
> “Implement `Product transform(Product p)` that uppercases names, applies 10% discount to Electronics with HALF_UP rounding to 2 decimals, and recategorizes to ‘Premium Electronics’ if discounted price > 500. Also provide a method to compute `PriceRange` with thresholds 10/100/500.”

**AI response (short excerpt):**  
> “Use `price.multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_UP)`. Compare with `500.00` for premium recategorization. For `PriceRange`, return Low/Medium/High/Premium based on `≤` thresholds.”

**What I accepted/modified:**  
- Accepted the rounding and thresholds verbatim to preserve A2 behavior.  
- Ensured the comparison is strictly `> 500.00` (not `≥`), as in A2.

---

### Prompt 3 — “CSV reader that tolerates commas, semicolons, or TABs; strip `$` in prices”
**My prompt (excerpt):**  
> “Show a simple `CsvProductReader` that: skips blank/malformed rows, splits on `[,;\\t]`, strips `$` from price, and returns a `List<Product>`. Keep relative input path.”

**AI response (short excerpt):**  
> “In `readAll()`, use `Files.newBufferedReader`, read the header, then split with `line.split("[,;\\t]", -1)`. Parse `int`/`BigDecimal` inside a try/catch and skip on failure.”

**What I accepted/modified:**  
- Accepted the split/strip approach.  
- Added a small `strip()` helper to trim quotes and whitespace.

---

### Prompt 4 — “CSV writer that outputs header + rows”
**My prompt (excerpt):**  
> “Implement `CsvProductWriter` that writes the header `ProductID,Name,Price,Category,PriceRange` and each transformed row, creating parent directories if needed.”

**AI response (short excerpt):**  
> “Use `Files.createDirectories(outputPath.getParent())` and a `BufferedWriter`. Join fields with `String.join(\",\", r)`.”

**What I accepted/modified:**  
- Accepted as-is and verified the header line matched A2 exactly.

---

### Prompt 5 — “Prove A3 matches A2: what test cases should I run?”
**My prompt (excerpt):**  
> “List concise tests to confirm functional equivalence: same outputs and console summary counts.”

**AI response (short excerpt):**  
> “Use happy path with mixed delimiters, dollar sign prices, malformed rows, empty header-only file, and missing input file. Compare outputs byte-for-byte or via diff.”

**What I accepted/modified:**  
- Adopted the test list; also added boundary price checks at 10/100/500.

---

### Prompt 6 — “Javadoc phrasing and Eclipse run configuration tips”
**My prompt (excerpt):**  
> “Give short Javadoc templates for my interfaces and a reminder about working directory for relative paths.”

**AI response (short excerpt):**  
> “Document purpose and behavior of each class and public method. In Eclipse, set Working Directory to project root so `data/` resolves correctly.”

**What I accepted/modified:**  
- Used the Javadoc templates but edited wording to reflect my actual code choices.  
- Verified the working directory so the program finds `data/products.csv`.

---

## Reflection on AI usage (ethics & integrity)
- I used AI **as a helper**, not a replacement for my own work. I validated code behavior against A2 outputs and edited all text for accuracy and my own voice.
- I avoided copying large blocks blindly; every change was compiled, run, and checked with test cases.


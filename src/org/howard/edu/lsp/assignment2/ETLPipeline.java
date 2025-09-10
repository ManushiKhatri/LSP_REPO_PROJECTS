//Name: Manushi Khatri
//CSCI 363/540 - Assignment 2: ETL Pipeline With Relative Paths

package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {
    // Simple record for one CSV row after parsing
    private static class Product {
        int productId;
        String name;
        BigDecimal price; // use BigDecimal for currency-safe math
        String category;
        Product(int id, String name, BigDecimal price, String category) {
            this.productId = id;
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    public static void main(String[] args) {
        // Relative paths so the project runs the same on any machine
        Path inputPath = Paths.get("data", "products.csv");
        Path outputPath = Paths.get("data", "transformed_products.csv");

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;
        List<Product> products = new ArrayList<>();
        
        // Fail fast if input is missing
        if (!Files.exists(inputPath)) {
            System.err.println("ERROR: Missing input file at " + inputPath.toString());
            return;
        }
        // Extract: read header then parse each data row
        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String line = br.readLine();  // header (not transformed)
            if (line == null) {  // empty file -> write header-only output
                writeOutput(outputPath, new ArrayList<>());
                printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath);
                return;
            }
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                rowsRead++;
                // Be tolerant to Excel: accept comma, semicolon, or TAB
                String[] parts = line.split("[,;\\t]", -1);
                if (parts.length != 4) {  
                    rowsSkipped++;
                    continue;
                }
                try {
                    int id = Integer.parseInt(strip(parts[0]));
                    String name = strip(parts[1]);
                    // Strip $ if present, then parse as BigDecimal
                    BigDecimal price = new BigDecimal(strip(parts[2]).replace("$",""));
                    String category = strip(parts[3]);
                    products.add(new Product(id, name, price, category));
                } catch (Exception e) {  // any parse failure -> skip row
                    rowsSkipped++;
                }
            }
            // If file has only header row
            if (rowsRead == 0) {
                writeOutput(outputPath, new ArrayList<>());
                printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath);
                return;
            }
            
            // Transform then load
            List<String[]> transformedRows = new ArrayList<>();
            for (Product p : products) {
                Product t = transform(p);
                if (t != null) {
                    rowsTransformed++;
                    String priceRange = computePriceRange(t.price);
                    transformedRows.add(new String[] {
                            String.valueOf(t.productId),
                            t.name,
                            t.price.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                            t.category,
                            priceRange
                    });
                } else {
                    rowsSkipped++;
                }
            }
            // Load: write csv with header and comma separators
            writeOutput(outputPath, transformedRows);
        } catch (IOException e) {
            System.err.println("ERROR reading input: " + e.getMessage());
            return;
        }
        // End of run summary
        printSummary(rowsRead, rowsTransformed, rowsSkipped, outputPath);
    }

    private static String strip(String s) {
        String t = s.trim();
        if (t.startsWith("\"") && t.endsWith("\"") && t.length() >= 2) {
            t = t.substring(1, t.length()-1);
        }
        return t;
    }
    
    // Apply specified transforms:
    // 1. uppercase name
    // 2. 10% discount if original category == Electronics (HALF_UP to 2 decimals)
    // 3. if discounted price > 500 and original category == Electronics -> "Premium Electronics"
    private static Product transform(Product p) {
        if (p == null) return null;
        String originalCategory = p.category;
        String newName = p.name == null ? "" : p.name.toUpperCase();
        BigDecimal price = p.price;
        if ("Electronics".equalsIgnoreCase(originalCategory)) {
            price = price.multiply(BigDecimal.valueOf(0.9));
            price = price.setScale(2, RoundingMode.HALF_UP);
        } else {
            price = price.setScale(2, RoundingMode.HALF_UP);
        }
        String newCategory = originalCategory;
        if ("Electronics".equalsIgnoreCase(originalCategory)
                && price.compareTo(new BigDecimal("500.00")) > 0) {
            newCategory = "Premium Electronics";
        }
        return new Product(p.productId, newName, price, newCategory);
    }
    
    // Price Range:
    //≤10 → Low, ≤100 → Medium, ≤500 → High, else Premium
    private static String computePriceRange(BigDecimal finalPrice) {
        int cmpLow10 = finalPrice.compareTo(new BigDecimal("10.00"));
        int cmp100 = finalPrice.compareTo(new BigDecimal("100.00"));
        int cmp500 = finalPrice.compareTo(new BigDecimal("500.00"));
        if (cmpLow10 <= 0) return "Low";
        else if (cmp100 <= 0) return "Medium";
        else if (cmp500 <= 0) return "High";
        else return "Premium";
    }

    private static void writeOutput(Path outputPath, List<String[]> rows) {
        try {
            Files.createDirectories(outputPath.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();
                for (String[] r : rows) {
                    bw.write(String.join(",", r));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR writing output: " + e.getMessage());
        }
    }

    private static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped, Path outputPath) {
        System.out.println("Run Summary");
        System.out.println("-----------");
        System.out.println("Rows read:        " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped:     " + rowsSkipped);
        System.out.println("Output written:   " + outputPath.toString());
    }
}

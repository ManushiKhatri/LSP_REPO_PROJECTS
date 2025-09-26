package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

public class CsvProductReader implements ProductReader {
  private final Path inputPath;
  public CsvProductReader(Path inputPath) { this.inputPath = inputPath; }

  @Override public List<Product> readAll() throws IOException {
    if (!Files.exists(inputPath)) {
      System.err.println("ERROR: Missing input file at " + inputPath);
      return Collections.emptyList(); // main will handle summary
    }
    List<Product> out = new ArrayList<>();
    try (BufferedReader br = Files.newBufferedReader(inputPath)) {
      String line = br.readLine(); // header
      if (line == null) return out; // empty file -> header-only output later
      while ((line = br.readLine()) != null) {
        if (line.isBlank()) continue;
        String[] parts = line.split("[,;\\t]", -1);
        if (parts.length != 4) continue;
        try {
          int id = Integer.parseInt(strip(parts[0]));
          String name = strip(parts[1]);
          BigDecimal price = new BigDecimal(strip(parts[2]).replace("$",""));
          String category = strip(parts[3]);
          out.add(new Product(id, name, price, category));
        } catch (Exception ignore) { /* skip malformed row */ }
      }
    }
    return out;
  }
  private static String strip(String s) {
    String t = s.trim();
    if (t.startsWith("\"") && t.endsWith("\"") && t.length()>=2) t = t.substring(1,t.length()-1);
    return t;
  }
}

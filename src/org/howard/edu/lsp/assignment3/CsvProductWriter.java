package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class CsvProductWriter implements ProductWriter {
  private final Path outputPath;
  public CsvProductWriter(Path outputPath) { this.outputPath = outputPath; }

  @Override public void writeAll(List<String[]> rows) throws IOException {
    Files.createDirectories(outputPath.getParent());
    try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
      bw.write("ProductID,Name,Price,Category,PriceRange");
      bw.newLine();
      for (String[] r : rows) {
        bw.write(String.join(",", r));
        bw.newLine();
      }
    }
  }
}

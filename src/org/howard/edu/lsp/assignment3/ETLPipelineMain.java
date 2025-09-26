package org.howard.edu.lsp.assignment3;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.*;

public class ETLPipelineMain {
  public static void main(String[] args) {
    Path in = Path.of("data","products.csv");
    Path out = Path.of("data","transformed_products.csv");

    int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

    try {
      var reader = new CsvProductReader(in);
      var transformer = new DefaultProductTransformer();
      var writer = new CsvProductWriter(out);

      List<Product> products = reader.readAll();
      rowsRead = products.size();

      List<String[]> rows = new ArrayList<>();
      for (Product p : products) {
        Product t = transformer.transform(p);
        if (t == null) { rowsSkipped++; continue; }
        rowsTransformed++;
        var priceRange = transformer.priceRange(t.getPrice());
        rows.add(new String[] {
          String.valueOf(t.getProductId()),
          t.getName(),
          t.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString(),
          t.getCategory(),
          priceRange.name()
        });
      }
      writer.writeAll(rows);
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }

    System.out.println("Run Summary");
    System.out.println("-----------");
    System.out.println("Rows read:        " + rowsRead);
    System.out.println("Rows transformed: " + rowsTransformed);
    System.out.println("Rows skipped:     " + rowsSkipped);
    System.out.println("Output written:   data/transformed_products.csv");
  }
}

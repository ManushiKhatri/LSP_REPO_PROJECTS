package org.howard.edu.lsp.assignment3;

import java.math.*;

public class DefaultProductTransformer implements ProductTransformer {
  @Override public Product transform(Product p) {
    if (p == null) return null;
    String originalCategory = p.getCategory();
    String newName = p.getName()==null ? "" : p.getName().toUpperCase();
    BigDecimal price = p.getPrice();
    if ("Electronics".equalsIgnoreCase(originalCategory)) {
      price = price.multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_UP);
    } else {
      price = price.setScale(2, RoundingMode.HALF_UP);
    }
    String newCategory = originalCategory;
    if ("Electronics".equalsIgnoreCase(originalCategory)
        && price.compareTo(new BigDecimal("500.00")) > 0) {
      newCategory = "Premium Electronics";
    }
    return new Product(p.getProductId(), newName, price, newCategory);
  }

  @Override public PriceRange priceRange(BigDecimal price) {
    if (price.compareTo(new BigDecimal("10.00")) <= 0) return PriceRange.Low;
    if (price.compareTo(new BigDecimal("100.00")) <= 0) return PriceRange.Medium;
    if (price.compareTo(new BigDecimal("500.00")) <= 0) return PriceRange.High;
    return PriceRange.Premium;
  }
}

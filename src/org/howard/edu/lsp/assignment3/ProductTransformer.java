package org.howard.edu.lsp.assignment3;

public interface ProductTransformer {
  Product transform(Product p);
  PriceRange priceRange(java.math.BigDecimal price);
}

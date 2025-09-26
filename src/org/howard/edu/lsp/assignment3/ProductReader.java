package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/** Loads Products from some source. */
public interface ProductReader {
  List<Product> readAll() throws IOException;
}

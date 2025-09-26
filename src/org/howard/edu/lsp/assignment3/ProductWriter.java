package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

public interface ProductWriter {
  void writeAll(List<String[]> rows) throws IOException;
}

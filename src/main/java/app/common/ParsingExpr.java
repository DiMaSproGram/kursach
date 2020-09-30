package app.common;

import app.payload.Hardware;

import java.io.IOException;

public interface ParsingExpr {
  void parse(Hardware hardware) throws IOException;
}

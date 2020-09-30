package app.common;

import app.payload.Hardware;
import lombok.SneakyThrows;

public class ThreadExec implements Runnable  {

  private ParsingExpr parsing;
  private Hardware hardware;

  public ThreadExec(ParsingExpr parsing, Hardware hardware) {
    this.parsing = parsing;
    this.hardware = hardware;
  }

  @SneakyThrows
  @Override
  public void run() {
    parsing.parse(hardware);
  }
}

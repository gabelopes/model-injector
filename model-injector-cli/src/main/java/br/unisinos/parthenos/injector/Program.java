package br.unisinos.parthenos.injector;

import picocli.CommandLine;
import picocli.CommandLine.MissingParameterException;

public class Program {
  private static void process(CLI commandLineInterpreter) {
    final Processor processor = commandLineInterpreter.interpret();
    final boolean succeeded = processor.process();

    if (!succeeded) {
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    try {
      final CLI commandLineInterpreter = CommandLine.populateCommand(new CLI(), args);
      Program.process(commandLineInterpreter);
    } catch (MissingParameterException e) {
      System.out.println("Insufficient arguments!");
      System.exit(2);
    }
  }
}

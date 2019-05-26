package br.unisinos.parthenos.injector;

import br.unisinos.parthenos.injector.result.Result;
import picocli.CommandLine;
import picocli.CommandLine.MissingParameterException;

public class Program {
  private static void process(CLI commandLineInterpreter) {
    final Processor processor = commandLineInterpreter.interpret();
    final Result<?> result = processor.process();

    if (result.getOutput() != null) {
      System.out.println(result.getOutput());
    }

    System.exit(result.getStatus().getExitStatus());
  }

  public static void main(String[] args) {
    try {
      final CLI commandLineInterpreter = CommandLine.populateCommand(new CLI(), args);
      Program.process(commandLineInterpreter);
    } catch (MissingParameterException e) {
      System.out.println("Insufficient arguments!");
      System.exit(-1);
    }
  }
}

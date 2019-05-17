package br.unisinos.parthenos.injector;

import br.unisinos.parthenos.injector.enumeration.SourceLanguage;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Set;

@Getter
@Setter
public class CLI {
  @Option(names = {"-s", "--source"}, required = true)
  private File sourceFile;

  @Option(names = {"-l", "--language"}, required = true)
  private SourceLanguage sourceLanguage;

  @Option(names = {"-i", "--injection"}, required = true)
  private String injectionName;

  @Option(names = {"-e", "--extensions"}, required = true)
  private Set<File> extensions;

  @Parameters(arity = "1")
  private String JSON;

  public Processor interpret() {
    return new Processor(this.getSourceFile(), this.getSourceLanguage(), this.getInjectionName(), this.getExtensions(), this.getJSON());
  }
}

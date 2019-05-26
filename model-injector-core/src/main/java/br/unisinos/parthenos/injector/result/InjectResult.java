package br.unisinos.parthenos.injector.result;

import br.unisinos.parthenos.injector.enumeration.Status;
import lombok.Getter;

import java.io.File;

@Getter
public class InjectResult<T> extends Result<T> {
  private File outputFile;

  public InjectResult(Status status, T output, File outputFile) {
    super(status, output);
    this.outputFile = outputFile;
  }

  public InjectResult(Status status) {
    super(status);
  }

  public InjectResult(Status status, T output) {
    super(status, output);
  }
}

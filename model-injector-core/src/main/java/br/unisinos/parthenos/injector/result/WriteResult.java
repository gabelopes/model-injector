package br.unisinos.parthenos.injector.result;

import br.unisinos.parthenos.injector.enumeration.Status;
import lombok.Getter;

@Getter
public class WriteResult extends Result<String> {
  public WriteResult(Status status, String output) {
    super(status, output);
  }

  public WriteResult(Status status) {
    super(status);
  }
}

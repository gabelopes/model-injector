package br.unisinos.parthenos.injector.result;

import br.unisinos.parthenos.injector.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {
  private Status status;
  private T output;

  public Result(Status status) {
    this.status = status;
  }
}

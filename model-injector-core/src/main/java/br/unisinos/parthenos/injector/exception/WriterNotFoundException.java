package br.unisinos.parthenos.injector.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriterNotFoundException extends RuntimeException {
  private Class<?> outputClass;

  @Override
  public String getMessage() {
    return "Could not find writer for output of type '" + this.getOutputClass() + "'";
  }
}

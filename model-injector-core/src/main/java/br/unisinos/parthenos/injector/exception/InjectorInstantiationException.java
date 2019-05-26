package br.unisinos.parthenos.injector.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InjectorInstantiationException extends RuntimeException {
  private Class<?> injectorClass;
  private Class<?> modelClass;

  @Override
  public String getMessage() {
    return "Could not instance injector '" + this.getInjectorClass() + "' for model '" + this.getModelClass() + "'";
  }
}

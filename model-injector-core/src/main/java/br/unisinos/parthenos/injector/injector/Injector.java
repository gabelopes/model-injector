package br.unisinos.parthenos.injector.injector;

import br.unisinos.parthenos.injector.injector.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Injector<T, M extends Model> {
  private M model;

  public abstract boolean inject(T target);
}

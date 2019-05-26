package br.unisinos.parthenos.injector.injector;

import br.unisinos.parthenos.injector.injector.model.Model;
import br.unisinos.parthenos.injector.result.InjectResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@Getter
@AllArgsConstructor
public abstract class Injector<T, M extends Model> {
  private M model;

  public abstract InjectResult<?> inject(T target, File source);
}

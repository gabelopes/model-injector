package br.unisinos.parthenos.injector;

import br.unisinos.parthenos.injector.injector.Injector;
import br.unisinos.parthenos.injector.injector.model.Model;
import com.jsoniter.JsonIterator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Getter
@AllArgsConstructor
public class Interpreter {
  private Class<? extends Injector> injectorClass;
  private Class<? extends Model> modelClass;
  private String JSON;

  private Injector getInjector(Model model) {
    try {
      final Constructor<? extends Injector> injectorConstructor = this.getInjectorClass().getConstructor(this.getModelClass());
      return injectorConstructor.newInstance(model);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      return null;
    }
  }

  public Injector interpret() {
    final Model model = JsonIterator.deserialize(this.getJSON(), this.getModelClass());

    return this.getInjector(model);
  }
}

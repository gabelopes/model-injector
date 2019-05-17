package br.unisinos.parthenos.injector.injector.model;

import br.unisinos.parthenos.injector.annotation.Model;
import br.unisinos.parthenos.injector.injector.Injector;
import br.unisinos.parthenos.injector.reflection.Instance;

public class ModelFactory {
  public static Class<? extends br.unisinos.parthenos.injector.injector.model.Model> getModelClassFrom(Class<? extends Injector> injectorClass) {
    final Model modelAnnotation = injectorClass.getAnnotation(Model.class);

    if (modelAnnotation == null) { return null; }

    return modelAnnotation.value();
  }

  public static br.unisinos.parthenos.injector.injector.model.Model getModelFrom(Class<? extends Injector> injectorClass) {
    final Class<? extends br.unisinos.parthenos.injector.injector.model.Model> modelClass = ModelFactory.getModelClassFrom(injectorClass);

    return Instance.create(modelClass);
  }
}

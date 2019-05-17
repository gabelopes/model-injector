package br.unisinos.parthenos.injector.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
  Class<? extends br.unisinos.parthenos.injector.injector.model.Model> value();
}

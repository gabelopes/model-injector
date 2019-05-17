package br.unisinos.parthenos.injector.annotation;

import br.unisinos.parthenos.injector.enumeration.SourceLanguage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Language {
  SourceLanguage value();
}

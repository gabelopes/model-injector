package br.unisinos.parthenos.injector.parser;

import java.io.File;

public interface Parser<T> {
  T parse(File sourceFile);
}

package br.unisinos.parthenos.injector.io;

import java.io.File;

public interface Writer<T> {
  boolean write(File sourceFile, T injectedSource);
}

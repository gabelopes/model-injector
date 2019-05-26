package br.unisinos.parthenos.injector.io;

import br.unisinos.parthenos.injector.result.InjectResult;
import br.unisinos.parthenos.injector.result.WriteResult;

public interface Writer<T> {
  WriteResult write(InjectResult<?> injectResult);
}

package br.unisinos.parthenos.injector.reflection;

public class Instance {
  public static <T> T create(Class<? extends T> clazz) {
    if (clazz == null) { return null; }

    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
  }
}

package br.unisinos.parthenos.injector;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

public class Extension {
  private static final String ADD_URL_METHOD_NAME = "addURL";

  public static void include(File extension) {
    try {
      final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
      final Class<?> systemClassLoaderClass = systemClassLoader.getClass();
      final Method method = systemClassLoaderClass
        .getSuperclass()
        .getDeclaredMethod(ADD_URL_METHOD_NAME, URL.class);

      method.setAccessible(true);
      method.invoke(systemClassLoader, extension.toURI().toURL());
    } catch (Exception e) {
      System.out.println("Could not include extension '" + extension + "'");
    }

    System.out.println("Loaded extension '" + extension + "'");
  }

  public static void includeAll(Iterable<File> extensions) {
    extensions.forEach(Extension::include);
  }
}

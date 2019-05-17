package br.unisinos.parthenos.injector.representation;

public class Text {
  public static String prefixCamelCase(String prefix, String text) {
    if (prefix == null || text == null) { return null; }

    return prefix + Text.capitalize(text);
  }

  public static String capitalize(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }
}

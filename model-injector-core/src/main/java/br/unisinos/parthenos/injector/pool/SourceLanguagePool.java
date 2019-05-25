package br.unisinos.parthenos.injector.pool;

import org.reflections.Reflections;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SourceLanguagePool {
  private static final List<SourceLanguage> SOURCE_LANGUAGES;

  static {
    SOURCE_LANGUAGES = findSourceLanguages();
  }

  private static List<SourceLanguage> findSourceLanguages() {
    final Reflections reflections = new Reflections();

    return reflections.getSubTypesOf(SourceLanguage.class)
      .stream()
      .filter(Enum.class::isAssignableFrom)
      .map(Class::getEnumConstants)
      .flatMap(Stream::of)
      .collect(toList());
  }

  public static SourceLanguage get(String name) {
    return SOURCE_LANGUAGES
      .stream()
      .filter(sourceLanguage -> Objects.equals(sourceLanguage.getName(), name))
      .findFirst()
      .orElse(null);
  }
}

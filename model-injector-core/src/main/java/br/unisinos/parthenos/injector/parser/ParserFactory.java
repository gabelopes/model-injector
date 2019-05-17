package br.unisinos.parthenos.injector.parser;

import br.unisinos.parthenos.injector.annotation.Language;
import br.unisinos.parthenos.injector.annotation.Result;
import br.unisinos.parthenos.injector.enumeration.SourceLanguage;
import br.unisinos.parthenos.injector.reflection.Instance;
import org.reflections.Reflections;

import java.util.Set;

public class ParserFactory {
  private static Set<Class<? extends Parser>> getParserClasses() {
    return new Reflections().getSubTypesOf(Parser.class);
  }

  private static boolean isParserFor(Class<? extends Parser> parserClass, SourceLanguage sourceLanguage, Class<?> resultClass) {
    final Result resultAnnotation = parserClass.getAnnotation(Result.class);

    return ParserFactory.isParserFor(parserClass, sourceLanguage)
        && resultAnnotation != null
        && resultAnnotation.value().equals(resultClass);
  }

  private static boolean isParserFor(Class<? extends Parser> parserClass, SourceLanguage sourceLanguage) {
    final Language languageAnnotation = parserClass.getAnnotation(Language.class);

    return languageAnnotation != null
        && languageAnnotation.value() == sourceLanguage;
  }

  private static Class<? extends Parser> getParserClassFor(SourceLanguage sourceLanguage, Class<?> resultClass) {
    return ParserFactory.getParserClasses()
      .stream()
      .filter(parserClass -> isParserFor(parserClass, sourceLanguage, resultClass))
      .findFirst()
      .orElse(null);
  }

  private static Class<? extends Parser> getParserClassFor(SourceLanguage sourceLanguage) {
    return ParserFactory.getParserClasses()
      .stream()
      .filter(parserClass -> isParserFor(parserClass, sourceLanguage))
      .findFirst()
      .orElse(null);
  }

  @SuppressWarnings("unchecked")
  public static <T> Parser<T> getParserFor(SourceLanguage sourceLanguage, Class<T> resultClass) {
    final Class<? extends Parser> parserClass = ParserFactory.getParserClassFor(sourceLanguage, resultClass);

    return (Parser<T>) Instance.create(parserClass);
  }

  public static Parser<?> getParserFor(SourceLanguage sourceLanguage) {
    final Class<? extends Parser> parserClass = ParserFactory.getParserClassFor(sourceLanguage);

    return Instance.create(parserClass);
  }
}

package br.unisinos.parthenos.injector.io;

import br.unisinos.parthenos.injector.annotation.Language;
import br.unisinos.parthenos.injector.annotation.Target;
import br.unisinos.parthenos.injector.enumeration.SourceLanguage;
import br.unisinos.parthenos.injector.reflection.Instance;
import org.reflections.Reflections;

import java.util.Set;

public class WriterFactory {
  private static <T> boolean isWriterFor(Class<? extends Writer> writerClass, SourceLanguage sourceLanguage, Class<T> targetClass) {
    final Language languageAnnotation = writerClass.getAnnotation(Language.class);
    final Target targetAnnotation = writerClass.getAnnotation(Target.class);

    return languageAnnotation != null
        && languageAnnotation.value() == sourceLanguage
        && targetAnnotation != null
        && targetAnnotation.value().equals(targetClass);
  }

  private static Set<Class<? extends Writer>> getWriterClasses() {
    return new Reflections().getSubTypesOf(Writer.class);
  }

  @SuppressWarnings("unchecked")
  private static <T> Class<? extends Writer<T>> getWriterClassFor(SourceLanguage sourceLanguage, Class<T> targetClass) {
    return (Class<Writer<T>>) WriterFactory.getWriterClasses()
      .stream()
      .filter(writerClass -> isWriterFor(writerClass, sourceLanguage, targetClass))
      .findFirst()
      .orElse(null);
  }

  public static <T> Writer<T> getWriterFor(SourceLanguage sourceLanguage, Class<T> targetClass) {
    final Class<? extends Writer<T>> writerClass = WriterFactory.getWriterClassFor(sourceLanguage, targetClass);

    return Instance.create(writerClass);
  }

  public static Writer<?> getWriterFor(SourceLanguage sourceLanguage) {
    final Class<? extends Writer<?>> writerClass = WriterFactory.getWriterClassFor(sourceLanguage, null);

    return Instance.create(writerClass);
  }
}

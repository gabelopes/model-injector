package br.unisinos.parthenos.injector.injector;

import br.unisinos.parthenos.injector.annotation.Language;
import br.unisinos.parthenos.injector.annotation.Name;
import br.unisinos.parthenos.injector.pool.SourceLanguage;
import br.unisinos.parthenos.injector.reflection.Instance;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;

public class InjectorFactory {
  private static Set<Class<? extends Injector>> getInjectorClasses() {
    return new Reflections().getSubTypesOf(Injector.class);
  }

  private static boolean isInjectorFor(Class<? extends Injector> injectorClass, String name, SourceLanguage sourceLanguage) {
    final Name nameAnnotation = injectorClass.getAnnotation(Name.class);
    final Language languageAnnotation = injectorClass.getAnnotation(Language.class);

    return nameAnnotation != null
        && nameAnnotation.value().equals(name)
        && languageAnnotation != null
        && Objects.equals(languageAnnotation.value(), sourceLanguage.getName());
  }

  public static Class<? extends Injector> getInjectorClassFor(String name, SourceLanguage sourceLanguage) {
    return InjectorFactory.getInjectorClasses()
      .stream()
      .filter(injectorClass -> isInjectorFor(injectorClass, name, sourceLanguage))
      .findFirst()
      .orElse(null);
  }

  public static Injector getInjectorFor(String name, SourceLanguage sourceLanguage) {
    final Class<? extends Injector> injectorClass = InjectorFactory.getInjectorClassFor(name, sourceLanguage);

    return Instance.create(injectorClass);
  }
}

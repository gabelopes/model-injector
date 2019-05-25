package br.unisinos.parthenos.injector;

import br.unisinos.parthenos.injector.annotation.Target;
import br.unisinos.parthenos.injector.pool.SourceLanguage;
import br.unisinos.parthenos.injector.injector.Injector;
import br.unisinos.parthenos.injector.injector.InjectorFactory;
import br.unisinos.parthenos.injector.injector.model.Model;
import br.unisinos.parthenos.injector.injector.model.ModelFactory;
import br.unisinos.parthenos.injector.io.Writer;
import br.unisinos.parthenos.injector.io.WriterFactory;
import br.unisinos.parthenos.injector.parser.Parser;
import br.unisinos.parthenos.injector.parser.ParserFactory;
import br.unisinos.parthenos.injector.pool.SourceLanguagePool;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Processor {
  private File sourceFile;
  private String sourceLanguage;
  private String injectionName;
  private Set<File> extensions;
  private String JSON;

  private SourceLanguage translateSourceLanguage() {
    return SourceLanguagePool.get(this.getSourceLanguage());
  }

  private Class<? extends Injector> getInjectorClass() {
    return InjectorFactory.getInjectorClassFor(this.getInjectionName(), this.translateSourceLanguage());
  }

  private Class<? extends Model> getModelClass(Class<? extends Injector> injectorClass) {
    return ModelFactory.getModelClassFrom(injectorClass);
  }

  private Interpreter getInterpreter() {
    final Class<? extends Injector> injectorClass = this.getInjectorClass();
    final Class<? extends Model> modelClass = this.getModelClass(injectorClass);

    return new Interpreter(injectorClass, modelClass, this.getJSON());
  }

  private Injector<?, ?> getInjector() {
    final Interpreter interpreter = this.getInterpreter();

    return interpreter.interpret();
  }

  private Parser<?> getParser(Injector<?, ?> injector) {
    final Target targetAnnotation = injector.getClass().getAnnotation(Target.class);

    if (targetAnnotation == null) {
      return ParserFactory.getParserFor(this.translateSourceLanguage());
    }

    return ParserFactory.getParserFor(this.translateSourceLanguage(), targetAnnotation.value());
  }

  @SuppressWarnings("unchecked")
  private <T> T getInjectionTarget(Injector<T, ?> injector) {
    final Parser<T> parser = (Parser<T>) this.getParser(injector);

    return parser.parse(this.getSourceFile());
  }

  private <T> Writer<T> getWriter(Class<T> targetClass) {
    return WriterFactory.getWriterFor(this.translateSourceLanguage(), targetClass);
  }

  @SuppressWarnings("unchecked")
  private <T> boolean writeInjection(T target) {
    final Writer<T> writer = (Writer<T>) this.getWriter(target.getClass());

    return writer.write(this.getSourceFile(), target);
  }

  @SuppressWarnings("unchecked")
  public <T> boolean process() {
    Extension.includeAll(this.getExtensions());

    final Injector<T, ?> injector = (Injector<T, ?>) this.getInjector();
    final T injectionTarget = this.getInjectionTarget(injector);
    final boolean injected = injector.inject(injectionTarget);

    if (!injected) { return false; }

    return this.writeInjection(injectionTarget);
  }
}

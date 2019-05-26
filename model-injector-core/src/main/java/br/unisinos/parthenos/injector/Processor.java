package br.unisinos.parthenos.injector;

import br.unisinos.parthenos.injector.annotation.Target;
import br.unisinos.parthenos.injector.enumeration.Status;
import br.unisinos.parthenos.injector.exception.AbortedException;
import br.unisinos.parthenos.injector.exception.WriterNotFoundException;
import br.unisinos.parthenos.injector.result.InjectResult;
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
import br.unisinos.parthenos.injector.result.Result;
import br.unisinos.parthenos.injector.result.WriteResult;
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

  private boolean isSourceFile(File file) {
    return file != null
        && file.exists()
        && file.isFile();
  }

  @SuppressWarnings("unchecked")
  private <T> T getInjectionTarget(Injector<T, ?> injector) {
    if (!this.isSourceFile(this.getSourceFile())) { return null; }

    final Parser<T> parser = (Parser<T>) this.getParser(injector);

    if (parser == null) { return null; }

    return parser.parse(this.getSourceFile());
  }

  private <T> Writer<T> getWriter(Class<T> targetClass) {
    return WriterFactory.getWriterFor(this.translateSourceLanguage(), targetClass);
  }

  private WriteResult writeInjection(InjectResult<?> injectResult) {
    final Class<?> outputType = injectResult.getOutput().getClass();
    final Writer<?> writer = this.getWriter(outputType);

    if (writer == null) {
      throw new WriterNotFoundException(outputType);
    }

    return writer.write(injectResult);
  }

  @SuppressWarnings("unchecked")
  public <T> Result<?> process() {
    try {
      Extension.includeAll(this.getExtensions());

      final Injector<T, ?> injector = (Injector<T, ?>) this.getInjector();
      final T injectionTarget = this.getInjectionTarget(injector);
      final InjectResult<?> injectResult = injector.inject(injectionTarget, this.getSourceFile());

      if (injectResult.getStatus() != Status.SUCCESS) {
        return injectResult;
      }

      return this.writeInjection(injectResult);
    } catch (AbortedException exception) {
      return new Result<>(Status.ABORTED, exception.getMessage());
    } catch (Exception exception) {
      return new Result<>(Status.EXCEPTION, exception);
    }
  }
}

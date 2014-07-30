package de.axone.refactor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes( "de.axone.refactor.Refactor" )
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class EclipseRefactorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith( Refactor.class )) {
            Refactor refactorAnnotation = element.getAnnotation( Refactor.class );
            String message = "The method " + element.getSimpleName()
                       + " is marked as refactor: " + refactorAnnotation.toString() + ".";
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message);
        }
        return true;
    }
}
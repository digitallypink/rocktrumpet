package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents.ClassDescriptionAnnotationProcessor;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents.MethodsWithAnnotationsProcessor;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents.PageTitleAnnotationProcessor;
import pink.digitally.rocktrumpet.annotations.ClassDescription;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.lang.model.element.Element;

public class PageComponentsProcessorFactory {
    private final FileContentBuilder stringBuilder;
    private final Trees instance;
    private PageTitle annotation;

    PageComponentsProcessorFactory(FileContentBuilder stringBuilder, Trees instance) {
        this.stringBuilder = stringBuilder;
        this.instance = instance;
    }

    public PageComponentsProcessorFactory withPageTitle(PageTitle annotation) {
        this.annotation = annotation;
        new PageTitleAnnotationProcessor(annotation, stringBuilder)
                .process();
        return this;
    }

    public PageComponentsProcessorFactory withMethodDescription(Iterable<? extends Element> methods) {
        new MethodsWithAnnotationsProcessor(methods, instance, stringBuilder)
                .process();
        return this;
    }

    public PageComponentsProcessorFactory withClassDescription(ClassDescription classDescription, Element element) {
        new ClassDescriptionAnnotationProcessor(classDescription, element, instance, stringBuilder)
                .process();
        return this;
    }

    public String title() {
        return annotation.value();
    }

    public String toString() {
        return stringBuilder.asString();
    }

    public static PageComponentsProcessorFactory withPageTitle(PageTitle annotation, Trees instance, FileContentBuilder fileContentBuilder) {
        return new PageComponentsProcessorFactory(fileContentBuilder, instance)
                .withPageTitle(annotation);
    }
}

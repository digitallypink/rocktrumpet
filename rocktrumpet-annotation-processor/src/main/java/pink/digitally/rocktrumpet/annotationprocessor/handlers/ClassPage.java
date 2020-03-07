package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotationprocessor.helpers.AnnotationClasses;
import pink.digitally.rocktrumpet.annotations.ClassDescription;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

public class ClassPage {
    private final Element element;
    private final Trees trees;
    private final Name documentName;
    private final PageTitle annotation;
    private final Iterable<? extends Element> localElements;
    private final ClassDescription classDescription;
    private final FileContentBuilder fileContentBuilder;
    private final String allAnnotation;

    public ClassPage(Element element, Trees trees,
                     FileContentBuilder fileContentBuilder) {
        this.element = element;
        this.trees = trees;
        this.documentName = element.getSimpleName();
        this.annotation = element.getAnnotation(PageTitle.class);
        this.classDescription = element.getAnnotation(ClassDescription.class);
        this.fileContentBuilder = fileContentBuilder;
        this.localElements = trees.getScope(trees.getPath(element))
                .getEnclosingClass()
                .getEnclosedElements();
        this.allAnnotation = AnnotationClasses.annotationClassesRegex();
    }

    public PageDetails getPageDetails() {
        final PageComponentsProcessorFactory pageComponentsProcessorFactory = PageComponentsProcessorFactory
                .withPageTitle(annotation, trees, fileContentBuilder)
                .withClassDescription(classDescription, element)
                .withMethodDescription(localElements);

        return new PageDetails(annotation.documentNumber(),
                documentName,
                String.format("%s.%s", documentName, fileContentBuilder.fileNameExtension()),
                pageComponentsProcessorFactory.toString().replaceAll(allAnnotation, ""),
                pageComponentsProcessorFactory.title());
    }
}

package pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotations.Heading;
import pink.digitally.rocktrumpet.annotations.MethodDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.Optional;

public class MethodsWithAnnotationsProcessor implements Processor {
    private final Iterable<? extends Element> methodDescriptions;
    private final Trees instance;
    private final FileContentBuilder fileContentBuilder;

    public MethodsWithAnnotationsProcessor(Iterable<? extends Element> methodDescriptions, Trees instance, FileContentBuilder fileContentBuilder) {
        this.methodDescriptions = methodDescriptions;
        this.instance = instance;
        this.fileContentBuilder = fileContentBuilder;
    }

    @Override
    public void process() {
        for (Element methodDescriptionElement : methodDescriptions) {
            final MethodDescription methodDescription = methodDescriptionElement.getAnnotation(MethodDescription.class);
            final Heading heading = methodDescriptionElement.getAnnotation(Heading.class);

            Optional.ofNullable(heading).ifPresent(theHeading -> fileContentBuilder
                    .heading(theHeading.level(), theHeading.value()));

            Optional.ofNullable(methodDescription)
                    .ifPresent(annotation ->
                            appendToStringBuilder(annotation, methodDescriptionElement));

        }
    }

    private void appendToStringBuilder(MethodDescription methodDescription, Element methodDescriptionElement) {
        final String pre = methodDescription.pre();
        final String post = methodDescription.post();
        final MethodTree methodTree = instance.getTree((ExecutableElement) methodDescriptionElement);
        final String methodBody = methodTree.toString();

        fileContentBuilder
                .paragraph(pre)
                .code(methodBody)
                .paragraph(post);
    }
}

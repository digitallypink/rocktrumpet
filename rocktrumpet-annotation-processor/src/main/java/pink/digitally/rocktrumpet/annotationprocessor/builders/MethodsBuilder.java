package pink.digitally.rocktrumpet.annotationprocessor.builders;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotations.Heading;
import pink.digitally.rocktrumpet.annotations.MethodDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.Optional;

public class MethodsBuilder implements Builder {
    private static final String CODE_BLOCK = "\n```\n";
    private final Iterable<? extends Element> methodDescriptions;
    private final Trees instance;
    private final StringBuilder stringBuilder;
    private static final String METHOD_DESCRIPTION_ANNOTATION_REGEX = "@MethodDescription[\\s]?\\(.+\\)";
    private static final String HEADER_ANNOTATION_REGEX = "@Heading[\\s]?\\(.+\\)";

    public MethodsBuilder(Iterable<? extends Element> methodDescriptions, Trees instance, StringBuilder stringBuilder){
        this.methodDescriptions = methodDescriptions;
        this.instance = instance;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void build(){
        for (Element methodDescriptionElement : methodDescriptions) {
            final MethodDescription methodDescription = methodDescriptionElement.getAnnotation(MethodDescription.class);
            final Heading heading = methodDescriptionElement.getAnnotation(Heading.class);

            Optional.ofNullable(heading).ifPresent(theHeading -> stringBuilder
                    .append("\n\n")
                    .append(theHeading.level().markdown())
                    .append(theHeading.value())
                    .append("\n"));

            Optional.ofNullable(methodDescription)
                    .ifPresent(annotation ->
                            appendToStringBuilder(methodDescription, methodDescriptionElement));

        }
    }

    private void appendToStringBuilder(MethodDescription methodDescription, Element methodDescriptionElement){
        final String pre = methodDescription.pre();
        final String post = methodDescription.post();
        final MethodTree methodTree = instance.getTree((ExecutableElement) methodDescriptionElement);
        final String methodBody = methodTree.toString()
                .replaceAll(METHOD_DESCRIPTION_ANNOTATION_REGEX+"|"+HEADER_ANNOTATION_REGEX, "")
                .trim();

        stringBuilder
                .append("\n").append(pre).append("\n").append(CODE_BLOCK)
                .append(methodBody).append(CODE_BLOCK)
                .append(post).append("\n");
    }
}

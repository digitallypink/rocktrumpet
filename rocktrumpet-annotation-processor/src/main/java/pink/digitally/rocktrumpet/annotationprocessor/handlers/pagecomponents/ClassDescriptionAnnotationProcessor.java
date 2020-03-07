package pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents;

import com.sun.source.util.Trees;
import org.apache.commons.lang3.StringUtils;
import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotations.ClassDescription;

import javax.lang.model.element.Element;
import java.util.Optional;

public class ClassDescriptionAnnotationProcessor implements Processor {
    private final ClassDescription annotation;
    private final Element element;
    private final Trees trees;
    private final FileContentBuilder fileContentBuilder;

    public ClassDescriptionAnnotationProcessor(ClassDescription annotation,
                                               Element element,
                                               Trees trees,
                                               FileContentBuilder fileContentBuilder) {
        this.annotation = annotation;
        this.element = element;
        this.trees = trees;
        this.fileContentBuilder = fileContentBuilder;
    }

    @Override
    public void process() {
        if (annotation != null) {
            final String pre = annotation.pre();
            final String post = annotation.post();
            final String classBody = trees.getPath(element).getLeaf().toString();

            Optional.of(pre).filter(StringUtils::isNoneBlank)
                    .ifPresent(fileContentBuilder::paragraph);

            fileContentBuilder.code("java", classBody);

            Optional.of(post).filter(StringUtils::isNoneBlank)
                    .ifPresent(fileContentBuilder::paragraph);
        }
    }
}

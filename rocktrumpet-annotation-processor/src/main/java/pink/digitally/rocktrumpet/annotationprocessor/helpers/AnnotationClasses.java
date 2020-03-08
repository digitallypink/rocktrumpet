package pink.digitally.rocktrumpet.annotationprocessor.helpers;

import pink.digitally.rocktrumpet.annotations.ClassDescription;
import pink.digitally.rocktrumpet.annotations.Heading;
import pink.digitally.rocktrumpet.annotations.MethodDescription;
import pink.digitally.rocktrumpet.annotations.PageTitle;
import pink.digitally.rocktrumpet.annotations.Summary;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class AnnotationClasses {

    private static final List<Class<?>> ANNOTATION_CLASSES =
            asList(
                    ClassDescription.class,
                    Heading.class,
                    MethodDescription.class,
                    PageTitle.class,
                    Summary.class
            );

    private AnnotationClasses() {
    }

    public static String annotationClassesRegex() {
        return ANNOTATION_CLASSES.stream()
                .map(className -> "[\\n]?@" + className.getSimpleName() + "[\\s]?.*[\\n]?")
                .collect(Collectors.joining("|"));
    }
}

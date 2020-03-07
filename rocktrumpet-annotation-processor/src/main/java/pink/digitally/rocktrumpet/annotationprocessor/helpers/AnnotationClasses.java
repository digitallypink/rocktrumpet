package pink.digitally.rocktrumpet.annotationprocessor.helpers;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnnotationClasses {

    private static final String STRING_THAT_WILL_BE_UNMATCHED = "[-]{100}";

    private AnnotationClasses() {
    }

    public static String annotationClassesRegex() {
        final URL resource = Thread.currentThread().getContextClassLoader()
                .getResource("pink/digitally/rocktrumpet/annotations");
        if (resource != null) {
            final File file = new File(resource.getFile());
            final String[] classNames = file.list((dir, name) -> name.endsWith(".class"));
            return Optional.ofNullable(classNames).map(theClassNames -> Arrays.stream(theClassNames)
                    .map(className -> className.replaceAll(".class", ""))
                    .map(className -> "[\\n]?@" + className + "[\\s]?.*[\\n]?")
                    .collect(Collectors.joining("|")))
                    .orElse(STRING_THAT_WILL_BE_UNMATCHED);
        }
        return STRING_THAT_WILL_BE_UNMATCHED;
    }
}

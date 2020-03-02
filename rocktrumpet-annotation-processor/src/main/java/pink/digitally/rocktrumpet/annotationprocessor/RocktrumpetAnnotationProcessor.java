package pink.digitally.rocktrumpet.annotationprocessor;

//import com.google.auto.service.AutoService;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.CombinedBuilder;
import pink.digitally.rocktrumpet.annotations.Heading;
import pink.digitally.rocktrumpet.annotations.MethodDescription;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Set;

@SupportedAnnotationTypes(
        "pink.digitally.rocktrumpet.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RocktrumpetAnnotationProcessor extends AbstractProcessor {

    private String documentDirectory;
    private Trees instance;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        documentDirectory = System.getProperty("docs.path", System.getProperty("java.io.tmpdir"));
        instance = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> pageTitleElements = roundEnv.getElementsAnnotatedWith(PageTitle.class);
        Set<? extends Element> methods = roundEnv.getElementsAnnotatedWithAny(
                Set.of(MethodDescription.class,
                        Heading.class));
        //There can be ONLY one pageTitle per class
        for (Element pageTitleElement : pageTitleElements) {
            Name simpleName = pageTitleElement.getSimpleName();
            PageTitle annotation = pageTitleElement.getAnnotation(PageTitle.class);
            //TODO Move the writhing to another class.
            final String fileContents = CombinedBuilder.withPageTitle(annotation, instance)
                    .withMethodDescription(methods)
                    .toString();
            try {
                File file = new File(documentDirectory);
                if (!file.exists()) {
                    //TODO log the success or the failure of this.
                    file.mkdirs();
                }
                Files.write(new File(documentDirectory, simpleName.toString() + ".md").toPath(),
                        fileContents.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}

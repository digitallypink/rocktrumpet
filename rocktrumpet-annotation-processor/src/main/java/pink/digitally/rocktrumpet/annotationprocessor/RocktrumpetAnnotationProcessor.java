package pink.digitally.rocktrumpet.annotationprocessor;

import com.google.auto.service.AutoService;
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
        "pink.digitally.rocktrumpet.annotations.PageTitle")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RocktrumpetAnnotationProcessor extends AbstractProcessor {

    private String documentDirectory;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        //TODO maybe create a default path.
        documentDirectory = System.getProperty("docs.path");
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> pageTitleElements = roundEnv.getElementsAnnotatedWith(PageTitle.class);
        //There can be ONLY one pageTitle per class
        for (Element pageTitleElement : pageTitleElements) {
            Name simpleName = pageTitleElement.getSimpleName();
            PageTitle annotation = pageTitleElement.getAnnotation(PageTitle.class);
            //TODO Move the writhing to another class.
            StringBuilder stringBuilder = new StringBuilder().append("# ").append(annotation.value()).append("\n");

            try {
                File file = new File(documentDirectory);
                if(!file.exists()){
                    //TODO log the success or the failure of this.
                    file.mkdirs();
                }
                Files.write(new File(documentDirectory, simpleName.toString() + ".md").toPath(),
                        stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}

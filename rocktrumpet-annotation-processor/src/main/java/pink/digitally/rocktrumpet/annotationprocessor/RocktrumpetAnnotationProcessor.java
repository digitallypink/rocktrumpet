package pink.digitally.rocktrumpet.annotationprocessor;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.MarkdownFileBuilder;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.DocumentDetails;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.PageTitleHandler;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.TableOfContentsBuilder;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        final List<DocumentDetails> documentDetails = pageTitleElements.stream()
                .map(element -> new PageTitleHandler(element, instance).pageTitleAndContents())
                .collect(Collectors.toList());

        final DocumentDetails contents = new TableOfContentsBuilder(documentDetails, new MarkdownFileBuilder()).contents();

        documentDetails.forEach(documentDetail -> FileWriterHelper.writeFiles(documentDirectory, documentDetail));
        if (documentDetails.size() > 1) {
            FileWriterHelper.writeFiles(documentDirectory, contents);
        }

        return false;
    }
}

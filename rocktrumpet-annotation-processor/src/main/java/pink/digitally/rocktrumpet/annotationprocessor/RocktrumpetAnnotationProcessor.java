package pink.digitally.rocktrumpet.annotationprocessor;

import com.sun.source.util.Trees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pink.digitally.rocktrumpet.annotationprocessor.config.RocktrumpetProperties;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.ClassPage;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.PageDetails;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.TableOfContentsPage;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "pink.digitally.rocktrumpet.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RocktrumpetAnnotationProcessor extends AbstractProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger("rocktrumpet");
    private Trees instance;
    private RocktrumpetProperties rocktrumpetProperties;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        instance = Trees.instance(processingEnv);
        rocktrumpetProperties = new RocktrumpetProperties();
        LOGGER.debug("Initialized RocktrumpetAnnotationProcessor");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            LOGGER.debug("Processing Annotations");
            Set<? extends Element> pageTitleElements = roundEnv.getElementsAnnotatedWith(PageTitle.class);
            final List<PageDetails> pageDetails = pageTitleElements.stream()
                    .map(element -> new ClassPage(element, instance, rocktrumpetProperties.getFileContentBuilder()).getPageDetails())
                    .collect(Collectors.toList());
            final String documentPath = rocktrumpetProperties.getDocumentPath();


            pageDetails.forEach(documentDetail -> FileWriterHelper.writeFiles(documentPath, documentDetail));

            if (!pageDetails.isEmpty() && rocktrumpetProperties.createTableOfContents()) {
                final PageDetails contents = new TableOfContentsPage(pageDetails,
                        rocktrumpetProperties, rocktrumpetProperties.getFileContentBuilder())
                        .getPageDetails();
                FileWriterHelper.writeFiles(documentPath, contents);
            }

            LOGGER.info("Wrote to directory: {}", documentPath);
        }
        return false;
    }
}

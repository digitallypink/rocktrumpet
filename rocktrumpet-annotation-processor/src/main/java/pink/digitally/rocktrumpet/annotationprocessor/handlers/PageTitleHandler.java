package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotationprocessor.builders.CombinedBuilder;
import pink.digitally.rocktrumpet.annotations.PageTitle;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

public class PageTitleHandler {
    private final Element element;
    private final Trees trees;
    private final Name documentName;
    private final PageTitle annotation;
    private final Iterable<? extends Element> localElements;

    public PageTitleHandler(Element element, Trees trees) {
        this.element = element;
        this.trees = trees;
        this.documentName = element.getSimpleName();
        annotation = element.getAnnotation(PageTitle.class);
        localElements = trees.getScope(trees.getPath(element))
                .getEnclosingClass()
                .getEnclosedElements();
    }

    public DocumentDetails pageTitleAndContents() {
        return new DocumentDetails(annotation.documentNumber(),
                //Abstract the naming convention and the builder to something else
                //So it can be in other formats.
                documentName,
                String.format("%s.md", documentName),
                CombinedBuilder
                        .withPageTitle(annotation, trees)
                        .withMethodDescription(localElements).toString());
    }
}

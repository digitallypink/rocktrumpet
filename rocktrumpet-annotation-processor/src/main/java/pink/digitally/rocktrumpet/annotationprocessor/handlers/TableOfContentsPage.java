package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotationprocessor.config.RocktrumpetProperties;
import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TableOfContentsPage {
    private static final String TABLE_OF_CONTENTS_HEADER = "Table of contents";
    private final Collection<PageDetails> pageDetails;
    private final RocktrumpetProperties rocktrumpetProperties;
    private final FileContentBuilder fileBuilder;

    public TableOfContentsPage(Collection<PageDetails> pageDetails, RocktrumpetProperties rocktrumpetProperties, FileContentBuilder fileBuilder) {
        this.pageDetails = pageDetails;
        this.rocktrumpetProperties = rocktrumpetProperties;
        this.fileBuilder = fileBuilder;
    }

    public PageDetails getPageDetails() {
        rocktrumpetProperties.getDocumentName()
                .ifPresent(fileBuilder::title);
        fileBuilder.heading(HeadingLevel.H2, TABLE_OF_CONTENTS_HEADER);

        final List<String> pagesList = pageDetails.stream()
                .sorted()
                .map(documentDetail ->
                        fileBuilder.anchorFrom(
                                documentDetail.getTitle(),
                                documentDetail.getFileName().toString()))
                .collect(Collectors.toList());

        fileBuilder.orderedList(pagesList);

        return new PageDetails("", "TableOfContents", "TableOfContents." + fileBuilder.fileNameExtension(),
                fileBuilder.asString(), "");
    }
}

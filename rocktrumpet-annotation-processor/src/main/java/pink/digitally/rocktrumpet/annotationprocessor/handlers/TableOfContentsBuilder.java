package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import pink.digitally.rocktrumpet.annotationprocessor.builders.MarkdownFileBuilder;

import java.util.Collection;

public class TableOfContentsBuilder {
    private static final String TABLE_OF_CONTENTS_HEADER = "Table of contents";
    private final Collection<DocumentDetails> documentDetails;
    private final MarkdownFileBuilder fileBuilder;

    public TableOfContentsBuilder(Collection<DocumentDetails> documentDetails, MarkdownFileBuilder fileBuilder) {
        this.documentDetails = documentDetails;
        this.fileBuilder = fileBuilder;
    }

    public DocumentDetails contents() {
        fileBuilder.headingTwo(TABLE_OF_CONTENTS_HEADER);
        documentDetails.stream()
                .sorted()
                .forEach(documentDetail ->
                        fileBuilder.orderedList(documentDetail.getDocumentNumber(),
                        String.format("[%s](%s)%n",
                        documentDetail.getDocumentName(),
                        documentDetail.getFileName())));


        return new DocumentDetails("", "TableOfContents", "TableOfContents.md",
                fileBuilder.build());
    }
}

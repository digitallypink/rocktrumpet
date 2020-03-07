package pink.digitally.rocktrumpet.annotationprocessor.handlers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

public class PageDetails implements Comparable<PageDetails> {
    private final CharSequence documentName;
    private final CharSequence fileName;
    private final CharSequence fileContents;
    private final String documentNumber;
    private final String title;

    public PageDetails(String documentNumber,
                       CharSequence documentName,
                       CharSequence fileName,
                       CharSequence fileContents, String title) {
        this.documentNumber = documentNumber;
        this.documentName = documentName;
        this.fileName = fileName;
        this.fileContents = fileContents;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public CharSequence getDocumentName() {
        return documentName;
    }

    public CharSequence getFileName() {
        return fileName;
    }

    public CharSequence getFileContents() {
        return fileContents;
    }

    public CharSequence getDocumentNumber() {
        return documentNumber;
    }

    @Override
    public int compareTo(@NotNull PageDetails o) {
        return this.documentNumber != null && o.documentNumber != null ?
                this.documentNumber.compareTo(o.documentNumber) : -1;
    }

    @SuppressWarnings("all")
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

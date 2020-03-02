package pink.digitally.rocktrumpet.annotationprocessor.handlers;

public class DocumentDetails implements Comparable<DocumentDetails> {
    private final CharSequence documentName;
    private final CharSequence fileName;
    private final CharSequence fileContents;
    private final String documentNumber;

    public DocumentDetails(String documentNumber,
                           CharSequence documentName,
                           CharSequence fileName,
                           CharSequence fileContents) {
        this.documentNumber = documentNumber;
        this.documentName = documentName;
        this.fileName = fileName;
        this.fileContents = fileContents;
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
    public int compareTo(DocumentDetails o) {
        return this.documentNumber != null && o.documentNumber != null ?
                this.documentNumber.compareTo(o.documentNumber) : -1;
    }
}

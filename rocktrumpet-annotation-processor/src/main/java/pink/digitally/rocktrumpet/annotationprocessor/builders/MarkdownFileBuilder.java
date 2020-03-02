package pink.digitally.rocktrumpet.annotationprocessor.builders;

import pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements;
import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.BLOCKQUOTES;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.TITLE;

public class MarkdownFileBuilder {
    public static final String NEW_LINE = "\n";
    private final StringBuilder stringBuilder;

    public MarkdownFileBuilder() {
        stringBuilder = new StringBuilder();
    }

    public MarkdownFileBuilder title(String titleBody) {
        appendElement(TITLE, titleBody);
        return this;
    }

    public MarkdownFileBuilder blockquotes(String body) {
        appendElement(BLOCKQUOTES, body);
        return this;
    }

    public MarkdownFileBuilder headingTwo(String value) {
        return heading(HeadingLevel.H2, value);
    }

    public MarkdownFileBuilder heading(HeadingLevel headingLevel, String value) {
        stringBuilder.append(headingLevel.markdown())
                .append(value)
                .append(NEW_LINE);
        return this;
    }

    public MarkdownFileBuilder orderedList(CharSequence documentNumber, String value) {
        stringBuilder.append(documentNumber)
                .append(". ")
                .append(value)
                .append(NEW_LINE);
        return this;
    }

    public String build() {
        return stringBuilder.toString();
    }

    private void appendElement(MarkdownElements markdownElement, String body) {
        stringBuilder.append(markdownElement.getPrefix())
                .append(body)
                .append(markdownElement.getSuffix());
    }
}

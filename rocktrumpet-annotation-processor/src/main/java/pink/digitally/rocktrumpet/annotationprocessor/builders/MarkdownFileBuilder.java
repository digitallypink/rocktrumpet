package pink.digitally.rocktrumpet.annotationprocessor.builders;

import pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements;
import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.BLOCKQUOTES;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.CODE_BLOCKS;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.HORIZONAL_LINE_RULE;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.ORDERED_LIST;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.TITLE;
import static pink.digitally.rocktrumpet.annotationprocessor.markupelements.MarkdownElements.UNORDERED_LIST;

public class MarkdownFileBuilder implements FileContentBuilder {
    public static final String NEW_LINE = "\n";
    private final StringBuilder stringBuilder;

    public MarkdownFileBuilder() {
        stringBuilder = new StringBuilder();
    }

    @Override
    public MarkdownFileBuilder title(String titleBody) {
        appendElement(TITLE, titleBody);
        return this;
    }

    @Override
    public MarkdownFileBuilder blockquote(String body) {
        appendElement(BLOCKQUOTES, body);
        return this;
    }

    @Override
    public FileContentBuilder code(String type, String content) {
        appendElement(CODE_BLOCKS.formatPrefix(type), content);
        return this;
    }

    @Override
    public FileContentBuilder horizontalRule() {
        appendElement(HORIZONAL_LINE_RULE, "");
        return this;
    }

    @Override
    public FileContentBuilder unorderedList(Iterable<String> listElements) {
        listElements.forEach(string -> appendElement(UNORDERED_LIST, string));
        return this;
    }

    @Override
    public FileContentBuilder orderedList(Iterable<String> listElements) {
        listElements.forEach(string -> appendElement(ORDERED_LIST, string));
        return this;
    }

    @Override
    public FileContentBuilder image(String label, String source, String alternateText) {
        return this;
    }

    @Override
    public FileContentBuilder lineBreak() {
        stringBuilder.append(NEW_LINE);
        return null;
    }

    @Override
    public FileContentBuilder paragraph(String paragraph) {
        stringBuilder.append(NEW_LINE)
        .append(paragraph)
        .append(NEW_LINE);
        return this;
    }

    @Override
    public String anchorFrom(String label, String link) {
        return String.format("[%s](%s)", label, link);
    }

    @Override
    public String fileNameExtension() {
        return "md";
    }

    @Override
    public String asString() {
        return stringBuilder.toString();
    }

    public MarkdownFileBuilder headingTwo(String value) {
        return heading(HeadingLevel.H2, value);
    }

    @Override
    public MarkdownFileBuilder heading(HeadingLevel headingLevel, String value) {
        stringBuilder.append(headingLevel.markdown())
                .append(value)
                .append(NEW_LINE);
        return this;
    }

    @Override
    public FileContentBuilder anchor(String label, String link) {
        stringBuilder.append(anchorFrom(label, link));
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

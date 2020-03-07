package pink.digitally.rocktrumpet.annotationprocessor.builders;

import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

public interface FileContentBuilder {
    FileContentBuilder heading(HeadingLevel headingLevel, String content);
    FileContentBuilder anchor(String label, String link);
    FileContentBuilder blockquote(String content);
    FileContentBuilder code(String type, String content);
    FileContentBuilder horizontalRule();
    FileContentBuilder unorderedList(Iterable<String> listElements);
    FileContentBuilder orderedList(Iterable<String> listElements);
    FileContentBuilder image(String label, String source, String alternateText);
    FileContentBuilder lineBreak();
    FileContentBuilder paragraph(String paragraph);

    String anchorFrom(String label, String link);
    String fileNameExtension();
    String asString();

    default FileContentBuilder code(String content){
        return code("", content);
    }

    default FileContentBuilder title(String value){
        return heading(HeadingLevel.H1, value);
    }
}

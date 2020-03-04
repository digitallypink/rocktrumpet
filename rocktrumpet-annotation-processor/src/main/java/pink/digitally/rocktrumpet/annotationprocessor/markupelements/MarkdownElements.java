package pink.digitally.rocktrumpet.annotationprocessor.markupelements;

import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

public enum MarkdownElements {
    BOLD("**","**"),
    ITALIC("*", "*"),
    BLOCKQUOTES("> ", "\n"),
    UNORDERED_LIST("* ", "\n"),
    JAVA_CODE_BLOCKS("\n```java\n", "\n```\n"),
    HORIZONAL_LINE_RULE("\n---\n", ""),
    TITLE(HeadingLevel.H1);

    private final String prefix;
    private final String suffix;

    MarkdownElements(HeadingLevel headingLevel){
        this(headingLevel.markdown(), "\n");
    }

    MarkdownElements(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}

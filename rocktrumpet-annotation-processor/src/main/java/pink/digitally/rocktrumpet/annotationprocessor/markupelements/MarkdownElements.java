package pink.digitally.rocktrumpet.annotationprocessor.markupelements;

import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

public enum MarkdownElements {
    BOLD("**","**"),
    ITALIC("*", "*"),
    BLOCKQUOTES("> ", "\n"),
    UNORDERED_LIST("* ", "\n"),
    ORDERED_LIST("1. ", "\n"),
    CODE_BLOCKS("\n```%s\n", "\n```\n"),
    HORIZONAL_LINE_RULE("\n---\n", ""),
    TITLE(HeadingLevel.H1);

    private String prefix;
    private String suffix;

    MarkdownElements(HeadingLevel headingLevel){
        this(headingLevel.markdown(), "\n");
    }

    MarkdownElements(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public MarkdownElements formatPrefix(String item){
        prefix = String.format(prefix, item);
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}

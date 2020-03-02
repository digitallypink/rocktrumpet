package pink.digitally.rocktrumpet.annotations.types;

public enum HeadingLevel {
    H1("# "),
    H2("## "),
    H3("### "),
    H4("#### "),
    H5("##### "),
    H6("###### ");

    private String markdownValue;

    HeadingLevel(String markdownValue) {
        this.markdownValue = markdownValue;
    }

    public String markdown() {
        return markdownValue;
    }
}

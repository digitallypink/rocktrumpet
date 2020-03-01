package pink.digitally.rocktrumpet.annotationprocessor.builders;

import org.apache.commons.lang3.StringUtils;
import pink.digitally.rocktrumpet.annotations.PageTitle;

public class PageTitleBuilder {
    private final PageTitle pageTitle;
    private final StringBuilder stringBuilder;

    public PageTitleBuilder(PageTitle pageTitle, StringBuilder stringBuilder) {
        this.pageTitle = pageTitle;
        this.stringBuilder = stringBuilder;
    }

    public void build(){
        stringBuilder.append("# ")
                .append(pageTitle.value())
                .append("\n");

        if(StringUtils.isNoneBlank(pageTitle.subHeading())){
            stringBuilder
                    .append("### ")
                    .append(pageTitle.subHeading())
                    .append("\n");
        }

        if(StringUtils.isNoneBlank(pageTitle.summary().value())){
            stringBuilder
                    .append("> ")
                    .append(pageTitle.summary().value())
                    .append("\n");
        }
    }
}

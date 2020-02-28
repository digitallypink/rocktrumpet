package pink.digitally.rocktrumpet.annotationprocessor.builders;

import org.apache.commons.lang3.StringUtils;
import pink.digitally.rocktrumpet.annotations.PageTitle;

public class PageTitleBuilder {
    private final PageTitle pageTitle;

    public PageTitleBuilder(PageTitle pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
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

        return stringBuilder.toString();
    }
}

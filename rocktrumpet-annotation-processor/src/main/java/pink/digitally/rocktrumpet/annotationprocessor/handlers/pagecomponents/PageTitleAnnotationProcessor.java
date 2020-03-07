package pink.digitally.rocktrumpet.annotationprocessor.handlers.pagecomponents;

import org.apache.commons.lang3.StringUtils;
import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotations.PageTitle;
import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

public class PageTitleAnnotationProcessor implements Processor {
    private final PageTitle pageTitle;
    private final FileContentBuilder fileContentBuilder;

    public PageTitleAnnotationProcessor(PageTitle pageTitle, FileContentBuilder fileContentBuilder) {
        this.pageTitle = pageTitle;
        this.fileContentBuilder = fileContentBuilder;
    }

    @Override
    public void process(){
        fileContentBuilder.title(pageTitle.value());

        if(StringUtils.isNoneBlank(pageTitle.subHeading())){
            fileContentBuilder
                    .heading(HeadingLevel.H3, pageTitle.subHeading());
        }

        if(StringUtils.isNoneBlank(pageTitle.summary().value())){
            fileContentBuilder
                    .blockquote(pageTitle.summary().value());
        }
    }
}

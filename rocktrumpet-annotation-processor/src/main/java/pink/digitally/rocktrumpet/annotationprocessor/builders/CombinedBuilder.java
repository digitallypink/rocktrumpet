package pink.digitally.rocktrumpet.annotationprocessor.builders;

import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotations.PageTitle;

public class CombinedBuilder {
    private final StringBuilder stringBuilder;
    private PageTitle annotation;
    private Trees instance;

    private CombinedBuilder(StringBuilder stringBuilder, PageTitle annotation, Trees instance){
        this.stringBuilder = stringBuilder;
        this.annotation = annotation;
        this.instance = instance;
        new PageTitleBuilder(annotation, stringBuilder).build();
    }

    private AnotherHiddenBuilder toMethodsBuilder() {
        return new AnotherHiddenBuilder(stringBuilder,instance);
    }

    public static AnotherHiddenBuilder withPageTitle(PageTitle annotation, Trees instance){
        return new CombinedBuilder(new StringBuilder(), annotation, instance)
            .toMethodsBuilder();
    }
}

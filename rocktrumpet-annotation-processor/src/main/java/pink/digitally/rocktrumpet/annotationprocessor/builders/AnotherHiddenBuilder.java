package pink.digitally.rocktrumpet.annotationprocessor.builders;

import com.sun.source.util.Trees;

import javax.lang.model.element.Element;
import java.util.Set;

public class AnotherHiddenBuilder {
    private final StringBuilder stringBuilder;
    private final Trees instance;

    AnotherHiddenBuilder(StringBuilder stringBuilder, Trees instance){
        this.stringBuilder = stringBuilder;
        this.instance = instance;
    }

    public AnotherHiddenBuilder withMethodDescription(Set<? extends Element> methods){
        new MethodsBuilder(methods, instance, stringBuilder).build();
        return this;
    }

    public String toString(){
        return stringBuilder.toString();
    }
}

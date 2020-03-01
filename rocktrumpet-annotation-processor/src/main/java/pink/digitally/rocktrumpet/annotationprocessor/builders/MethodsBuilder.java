package pink.digitally.rocktrumpet.annotationprocessor.builders;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.Trees;
import pink.digitally.rocktrumpet.annotations.MethodDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.Collection;

public class MethodsBuilder implements Builder {
    private final Collection<? extends Element> methodDescriptions;
    private final Trees instance;
    private final StringBuilder stringBuilder;
    public static final String FORMAT = "@MethodDescription[\\s]?\\(.+\\)";

    public MethodsBuilder(Collection<? extends Element> methodDescriptions, Trees instance, StringBuilder stringBuilder){
        this.methodDescriptions = methodDescriptions;
        this.instance = instance;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void build(){
        for (Element methodDescriptionElement : methodDescriptions) {
            final MethodDescription methodDescription = methodDescriptionElement.getAnnotation(MethodDescription.class);
            final String pre = methodDescription.pre();
            final String post = methodDescription.post();
            final MethodTree methodTree = instance.getTree((ExecutableElement) methodDescriptionElement);
            final String methodBody = methodTree.toString().replaceAll(FORMAT, "").trim();
            stringBuilder.append("\n").append(pre).append("\n").append("\n```\n")
                    .append(methodBody).append("\n```\n")
                    .append(post);
        }
    }
}

package pink.digitally.rocktrumpet.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(value = {ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ClassDescription {
    String pre() default "";
    String post() default "";
}

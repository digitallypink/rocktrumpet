package pink.digitally.rocktrumpet.annotations;

import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Heading {
    String value();
    HeadingLevel level();
}

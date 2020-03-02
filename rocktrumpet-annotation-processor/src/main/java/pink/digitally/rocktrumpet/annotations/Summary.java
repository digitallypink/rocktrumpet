package pink.digitally.rocktrumpet.annotations;

import java.lang.annotation.*;

/**
 * A brief summary of what to expect in a class.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Summary {
    String value();
}

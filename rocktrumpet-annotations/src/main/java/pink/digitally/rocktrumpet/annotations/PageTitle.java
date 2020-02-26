package pink.digitally.rocktrumpet.annotations;

import java.lang.annotation.*;

/**
 * The page title will be found in the generated docs.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface PageTitle {
    /**
     *
     * @return Page Title
     */
    String value();

    /**
     * Small description of the topic that will be discussed
     * @return Summary
     */
    Summary summary() default @Summary("");

    /**
     * This is used to inform the Table of contents
     * @return document number
     */
    String documentNumber();
}

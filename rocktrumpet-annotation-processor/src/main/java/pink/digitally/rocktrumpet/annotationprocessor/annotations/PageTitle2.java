package pink.digitally.rocktrumpet.annotationprocessor.annotations;

import pink.digitally.rocktrumpet.annotations.Summary;

public @interface PageTitle2 {
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

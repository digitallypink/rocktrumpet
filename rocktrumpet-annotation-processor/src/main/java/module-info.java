import pink.digitally.rocktrumpet.annotationprocessor.RocktrumpetAnnotationProcessor;

import javax.annotation.processing.Processor;

module rocktrumpet.annotation.processor {
    requires jdk.compiler;
    requires org.apache.commons.lang3;
    requires com.squareup.javapoet;
    requires org.jetbrains.annotations;
    requires org.slf4j;

    exports pink.digitally.rocktrumpet.annotationprocessor;
    exports pink.digitally.rocktrumpet.annotations;
    exports pink.digitally.rocktrumpet.annotations.types;

    opens pink.digitally.rocktrumpet.annotationprocessor;
    opens pink.digitally.rocktrumpet.annotationprocessor.builders;
    opens pink.digitally.rocktrumpet.annotations;

    uses Processor;
    provides Processor with RocktrumpetAnnotationProcessor;
}
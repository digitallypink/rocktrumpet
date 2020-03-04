import pink.digitally.rocktrumpet.annotationprocessor.RocktrumpetAnnotationProcessor;

import javax.annotation.processing.Processor;

module rocktrumpet.annotation.processor {
    requires jdk.compiler;
    requires org.apache.commons.lang3;
    requires com.squareup.javapoet;
    exports pink.digitally.rocktrumpet.annotationprocessor;
    exports pink.digitally.rocktrumpet.annotations;
    exports pink.digitally.rocktrumpet.annotations.types;

    opens pink.digitally.rocktrumpet.annotationprocessor;

    //Tests fail in maven if this is not opened
    opens pink.digitally.rocktrumpet.annotationprocessor.builders;

    //This is needed to actually provide the service externally. If this isn't present
    //Users of this will need to explicitly add this annotation processor
    uses Processor;
    provides Processor with RocktrumpetAnnotationProcessor;
}
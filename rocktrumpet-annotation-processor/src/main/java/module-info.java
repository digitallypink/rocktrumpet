module rocktrumpet.annotation.processor {
    requires jdk.compiler;
    requires rocktrumpet.annotations;
    requires auto.service.annotations;
    requires auto.service;
    requires org.apache.commons.lang3;
    opens pink.digitally.rocktrumpet.annotationprocessor;
    exports pink.digitally.rocktrumpet.annotationprocessor;
    exports pink.digitally.rocktrumpet.annotationprocessor.annotations;
}
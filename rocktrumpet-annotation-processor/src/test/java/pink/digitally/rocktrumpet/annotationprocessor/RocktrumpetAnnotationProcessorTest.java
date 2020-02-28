package pink.digitally.rocktrumpet.annotationprocessor;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


class RocktrumpetAnnotationProcessorTest {
    private String docsFolderPath = "./target/docs";
    private RocktrumpetAnnotationProcessor underTest;

    @BeforeEach
    public void setUp() {
        underTest = new RocktrumpetAnnotationProcessor();
        System.setProperty("docs.path", docsFolderPath);
    }

    @Test
    public void pageTitleIsATypeAnnotation() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines("pink.digitally.annotationprocessor.testclass.WrongLocation",
                "package pink.digitally.annotationprocessor.testclass;",
                "import pink.digitally.rocktrumpet.annotations.PageTitle;",
                "public class WrongLocation{",
                "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")",
                "public void aMethod(){}",
                "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .failsToCompile();
    }

    @Test
    public void onlyOnePageTitle() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines("pink.digitally.annotationprocessor.testclass.WrongLocation",
                "package pink.digitally.annotationprocessor.testclass;",
                "import pink.digitally.rocktrumpet.annotations.PageTitle;",
                "public class WrongLocation{",
                "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")",
                "@PageTitle(documentNumber = \"2\", value = \"Something else\")",
                "public void aMethod(){}",
                "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .failsToCompile();
    }

    @Test
    public void canPrintTitlePage() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines(
                "pink.digitally.rocktrumpet.annotationprocessor.testclass.HelloWorld",
                "package pink.digitally.rocktrumpet.annotationprocessor.testclass;\n" +
                        "\n" +
                        "import pink.digitally.rocktrumpet.annotations.PageTitle;\n" +
                        "\n" +
                        "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")\n" +
                        "public class HelloWorld {\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .compilesWithoutError();

        assertFileExists("HelloWorld.md");
        assertFileContains("HelloWorld.md", "Just The Title");
    }

    private void assertFileExists(String fileName) {
        File file = new File(docsFolderPath, fileName);
        assertTrue(String.format("Expected the file '%s' in classpath.", file.toPath()), file.exists());
    }

    private void assertFileContains(String fileName, String... strings){
        File file = new File(docsFolderPath, fileName);
        String fileBody = null;
        try {
            fileBody = Files.lines(file.toPath()).collect(Collectors.joining());
            assertTrue(Stream.of(strings).allMatch(fileBody::contains));
        } catch (IOException e) {
            fail(String.format("%s is missing.", file.toPath()));
        }
    }
}
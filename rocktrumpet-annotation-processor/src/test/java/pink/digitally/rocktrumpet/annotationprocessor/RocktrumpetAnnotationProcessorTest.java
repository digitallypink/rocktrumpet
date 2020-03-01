package pink.digitally.rocktrumpet.annotationprocessor;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


class RocktrumpetAnnotationProcessorTest {
    public static final String ANNOTATIONS_PAGE_TITLE = "import pink.digitally.rocktrumpet.annotations.PageTitle;\n";
    public static final String ROCKTRUMPET_ANNOTATIONPROCESSOR = "package pink.digitally.rocktrumpet.annotationprocessor;\n";
    private String docsFolderPath = "./target/docs";
    private RocktrumpetAnnotationProcessor underTest;
    private static File jarFile;

    @BeforeEach
    public void setUp() {
        underTest = new RocktrumpetAnnotationProcessor();

        System.setProperty("docs.path", docsFolderPath);
    }

    @BeforeAll
    public static void allTests() {
        jarFile = new File("/Users/ujuezeoke/projects/digitallypink/rocktrumpet/rocktrumpet-annotations/target/rocktrumpet-annotations-1.0-SNAPSHOT.jar");
        System.setProperty("com.google.common.truth.disable_stack_trace_cleaning", "true");
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
                        ANNOTATIONS_PAGE_TITLE +
                        "\n" +
                        "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")\n" +
                        "public class HelloWorld {\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .withClasspath(Collections.singleton(jarFile))
                .withCompilerOptions("-verbose")
                .processedWith(underTest)
                .compilesWithoutError();

        assertFileExists("HelloWorld.md");
        assertFileContains("HelloWorld.md", "Just The Title");
    }

    @Test
    public void pageTitleWithAllOptions() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString(
                "pink.digitally.rocktrumpet.annotationprocessor.FooBar",
                ROCKTRUMPET_ANNOTATIONPROCESSOR +
                        "\n" +
                        ANNOTATIONS_PAGE_TITLE +
                        "import pink.digitally.rocktrumpet.annotations.Summary;\n" +
                        "\n" +
                        "@PageTitle(value = \"Foo Bar\", documentNumber = \"1\", subHeading = \"A story of foo and bar\",\n" +
                        "summary = @Summary(\"One fine day in the month of May, Foo met Bar at a Bar and interesting things began to unfold.\"))\n" +
                        "public class FooBar {\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .compilesWithoutError();

        assertFileExists("FooBar.md");
        assertFileContains("FooBar.md",
                "Foo Bar",
                "A story of foo and bar",
                "One fine day in the month of May, Foo met Bar at a Bar and interesting things began to unfold.");
    }

    @Test
    public void methodDescription() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString(
                "pink.digitally.rocktrumpet.annotationprocessor.BartSimpson",
                ROCKTRUMPET_ANNOTATIONPROCESSOR +
                        "\n" +
                        "import pink.digitally.rocktrumpet.annotations.MethodDescription;\n" +
                        ANNOTATIONS_PAGE_TITLE +
                        "\n" +
                        "@PageTitle(documentNumber = \"1\", value = \"Bart Simpson\")\n" +
                        "public class BartSimpson {\n" +
                        "\n" +
                        "    @MethodDescription(pre = \"Bart as we all know enjoys being mischievous.\",\n" +
                        "    post = \"The above method demonstrates the act of Bart being mischievous.\")\n" +
                        "    public void performMischief(){\n" +
                        "        System.out.println(\"Ay Caramba!\");\n" +
                        "    }\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .withClasspath(Collections.singleton(jarFile))
                .processedWith(underTest)
                .compilesWithoutError();
        assertFileExists("BartSimpson.md");
        assertFileContains("BartSimpson.md",
                "Bart Simpson",
                "Bart as we all know enjoys being mischievous.",
                "public void performMischief()", "System.out.println(\"Ay Caramba!\");",
                "The above method demonstrates the act of Bart being mischievous.");
    }

    @Test
    public void multipleMethods() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString("pink.digitally.rocktrumpet.annotationprocessor.Doofenshmirtz",
                ROCKTRUMPET_ANNOTATIONPROCESSOR +
                "\n" +
                "import pink.digitally.rocktrumpet.annotations.MethodDescription;\n" +
                ANNOTATIONS_PAGE_TITLE +
                "\n" +
                "@PageTitle(value = \"I am Dr. Heinz Doofenshmirtz\", documentNumber = \"1\")\n" +
                "public class Doofenshmirtz {\n" +
                "    \n" +
                "    @MethodDescription(pre = \"Below we will demonstrate one of the things that Doof loves to do\")\n" +
                "    public void trapPerryThePlatypus(){\n" +
                "        if(aPlatypusIsWearingAHat()){\n" +
                "            System.out.println(\"You are trapped Perry the Platypus\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Where is Perry the Platypus\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @MethodDescription(pre = \"Only Doofenshmirtz knows how he determines if he has trapped Perry\",\n" +
                "            post = \"The method above is private.\")\n" +
                "    private boolean aPlatypusIsWearingAHat() {\n" +
                "        return true;\n" +
                "    }\n" +
                "}");
        assert_()
                .about(javaSource())
                .that(fileObject)
                .withClasspath(Collections.singleton(jarFile))
                .processedWith(underTest)
                .compilesWithoutError();
        assertFileExists("Doofenshmirtz.md");
        assertFileContains("Doofenshmirtz.md",
                "I am Dr. Heinz Doofenshmirtz",
                "Below we will demonstrate one of the things that Doof loves to do",
                "public void trapPerryThePlatypus()", "Only Doofenshmirtz knows how he determines if he has trapped Perry",
                "aPlatypusIsWearingAHat()");
    }

    private void assertFileExists(String fileName) {
        File file = new File(docsFolderPath, fileName);
        assertTrue(String.format("Expected the file '%s' in classpath.", file.toPath()), file.exists());
    }

    private void assertFileContains(String fileName, String... strings) {
        File file = new File(docsFolderPath, fileName);
        String fileBody;
        try (final Stream<String> lines = Files.lines(file.toPath())) {
            fileBody = lines.collect(Collectors.joining());
            for (String string : strings) {
                assertTrue(String.format("Expected a file body containing '%s'", string), fileBody.contains(string));
            }
        } catch (IOException e) {
            fail(String.format("%s is missing.", file.toPath()));
        }
    }
}
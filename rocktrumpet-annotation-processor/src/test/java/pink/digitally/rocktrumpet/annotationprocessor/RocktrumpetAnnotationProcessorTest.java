package pink.digitally.rocktrumpet.annotationprocessor;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@DisplayName("Rocktrumpet Annotation Processor should: ")
class RocktrumpetAnnotationProcessorTest {
    private static final String IMPORT_PAGE_TITLE = "import pink.digitally.rocktrumpet.annotations.PageTitle;\n";
    private static final String PACKAGE_DECLARATION = "package pink.digitally.rocktrumpet.annotationprocessor;\n";
    public static final String IMPORT_METHOD_DESCRIPTION = "import pink.digitally.rocktrumpet.annotations.MethodDescription;\n";
    private String docsFolderPath = "./target/docs";
    private RocktrumpetAnnotationProcessor underTest;

    @BeforeEach
    void setUp() {
        underTest = new RocktrumpetAnnotationProcessor();

        System.setProperty("docs.path", docsFolderPath);
    }

    @BeforeAll
    static void allTests() {
        System.setProperty("com.google.common.truth.disable_stack_trace_cleaning", "true");
    }

    @Test
    @DisplayName("process annotation only when PageTitle annotation is at class level")
    void pageTitleIsATypeAnnotation() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines("pink.digitally.annotationprocessor.testclass.WrongLocation",
                "package pink.digitally.annotationprocessor.testclass;",
                "import pink.digitally.rocktrumpet.annotations.PageTitle;",
                "class WrongLocation{",
                "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")",
                "void aMethod(){}",
                "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .failsToCompile();
    }

    @Test
    @DisplayName("process annotation only when PageTitle ONE annotation per class")
    void onlyOnePageTitle() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines("pink.digitally.annotationprocessor.testclass.WrongLocation",
                "package pink.digitally.annotationprocessor.testclass;",
                "import pink.digitally.rocktrumpet.annotations.PageTitle;",
                "class WrongLocation{",
                "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")",
                "@PageTitle(documentNumber = \"2\", value = \"Something else\")",
                "void aMethod(){}",
                "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .failsToCompile();
    }

    @Test
    @DisplayName("print a markdown file that contains the value of the @PageTitle")
    void canPrintTitlePage() {
        JavaFileObject fileObject = JavaFileObjects.forSourceLines(
                "pink.digitally.rocktrumpet.annotationprocessor.testclass.HelloWorld",
                "package pink.digitally.rocktrumpet.annotationprocessor.testclass;\n" +
                        "\n" +
                        IMPORT_PAGE_TITLE +
                        "\n" +
                        "@PageTitle(documentNumber = \"1\", value = \"Just The Title\")\n" +
                        "class HelloWorld {\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .compilesWithoutError();

        assertFileExists("HelloWorld.md");
        assertFileContains("HelloWorld.md", "Just The Title");
    }

    @Test
    @DisplayName("print all the elements of the @PageTitle annotation")
    void pageTitleWithAllOptions() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString(
                "pink.digitally.rocktrumpet.annotationprocessor.FooBar",
                PACKAGE_DECLARATION +
                        "\n" +
                        IMPORT_PAGE_TITLE +
                        "import pink.digitally.rocktrumpet.annotations.Summary;\n" +
                        "\n" +
                        "@PageTitle(value = \"Foo Bar\", documentNumber = \"1\", subHeading = \"A story of foo and bar\",\n" +
                        "summary = @Summary(\"One fine day in the month of May, Foo met Bar at a Bar and interesting things began to unfold.\"))\n" +
                        "class FooBar {\n" +
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
    @DisplayName("print details of method description when @MethodDescription")
    void methodDescription() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString(
                "pink.digitally.rocktrumpet.annotationprocessor.BartSimpson",
                PACKAGE_DECLARATION +
                        "\n" +
                        IMPORT_METHOD_DESCRIPTION +
                        IMPORT_PAGE_TITLE +
                        "\n" +
                        "@PageTitle(documentNumber = \"1\", value = \"Bart Simpson\")\n" +
                        "class BartSimpson {\n" +
                        "\n" +
                        "    @MethodDescription(pre = \"Bart as we all know enjoys being mischievous.\",\n" +
                        "    post = \"The above method demonstrates the act of Bart being mischievous.\")\n" +
                        "    void performMischief(){\n" +
                        "        System.out.println(\"Ay Caramba!\");\n" +
                        "    }\n" +
                        "}");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .compilesWithoutError();
        assertFileExists("BartSimpson.md");
        assertFileContains("BartSimpson.md",
                "Bart Simpson",
                "Bart as we all know enjoys being mischievous.",
                "void performMischief()", "System.out.println(\"Ay Caramba!\");",
                "The above method demonstrates the act of Bart being mischievous.");
    }

    @Test
    @DisplayName("print details of all method descriptions")
    void multipleMethods() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString("pink.digitally.rocktrumpet.annotationprocessor.Doofenshmirtz",
                PACKAGE_DECLARATION +
                        "\n" +
                        IMPORT_METHOD_DESCRIPTION +
                        IMPORT_PAGE_TITLE +
                        "\n" +
                        "@PageTitle(value = \"I am Dr. Heinz Doofenshmirtz\", documentNumber = \"1\")\n" +
                        "class Doofenshmirtz {\n" +
                        "    \n" +
                        "    @MethodDescription(pre = \"Below we will demonstrate one of the things that Doof loves to do\")\n" +
                        "    void trapPerryThePlatypus(){\n" +
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
                .processedWith(underTest)
                .compilesWithoutError();
        assertFileExists("Doofenshmirtz.md");
        assertFileContains("Doofenshmirtz.md",
                "I am Dr. Heinz Doofenshmirtz",
                "Below we will demonstrate one of the things that Doof loves to do",
                "void trapPerryThePlatypus()", "Only Doofenshmirtz knows how he determines if he has trapped Perry",
                "aPlatypusIsWearingAHat()");
    }

    @Test
    @DisplayName("print multiple headers and message bodies")
    void multipleMethodsMultipleHeaders() {
        JavaFileObject fileObject = JavaFileObjects.forSourceString("pink.digitally.rocktrumpet.annotationprocessor.ProgramingConditionalConcepts", PACKAGE_DECLARATION +
                "\n" +
                "import pink.digitally.rocktrumpet.annotations.Heading;\n" +
                IMPORT_METHOD_DESCRIPTION +
                IMPORT_PAGE_TITLE +
                "import pink.digitally.rocktrumpet.annotations.Summary;\n" +
                "import pink.digitally.rocktrumpet.annotations.types.HeadingLevel;\n" +
                "\n" +
                "@PageTitle(value = \"Programing Conditional Concepts\", documentNumber = \"1\",\n" +
                "        summary = @Summary(\"The document is going to focus on conditional behaviour\"))\n" +
                "class ProgramingConditionalConcepts {\n" +
                "\n" +
                "    @Heading(level = HeadingLevel.H2, value = \"If Statement\")\n" +
                "    @MethodDescription(pre = \"A standard if statement would perform an additional task ONLY when the condition in the if\" +\n" +
                "            \" block has been satisfied.\",\n" +
                "            post = \"When the above has been run, \\\"Do something that is unique to number five\\\" \" +\n" +
                "                    \"will be printed ONLY when the number passed is 5\")\n" +
                "    void ifStatement(int number) {\n" +
                "        if (5 == number) {\n" +
                "            System.out.println(\"Do something that is unique to number five\");\n" +
                "        }\n" +
                "        System.out.println(\"Something evey number does.\");\n" +
                "    }\n" +
                "\n" +
                "    @MethodDescription(pre = \"If-Else statements are used to pick between a number of conditions.\",\n" +
                "            post = \"In the example above, 'number % 5 == 0' is given the highest priority. \" +\n" +
                "                    \"For example if the number is 10 the result will include \\\"Divisible by 5\\\" and not \" +\n" +
                "                    \"\\\"Divisible by 2 and not divisible by 5\\\"\")\n" +
                "    void ifElseStatements(int number) {\n" +
                "        if (number % 5 == 0) {\n" +
                "            System.out.println(\"Divisible by 5\");\n" +
                "        } else if (number % 2 == 0) {\n" +
                "            System.out.println(\"Divisible by 2 and not divisible by 5\");\n" +
                "        }\n" +
                "        System.out.println(\"Something evey number does.\");\n" +
                "    }\n" +
                "    \n" +
                "    @Heading(level = HeadingLevel.H2, value = \"Switch Statements\")\n" +
                "    @MethodDescription(pre = \"This CAN be considered as a replacement for if-else-if statements.\",\n" +
                "    post = \"***Disclaimer use this technique with caution.\")\n" +
                "    void switchStatement(int number){\n" +
                "        switch (number){\n" +
                "            case 5: {\n" +
                "                System.out.println(\"Number is 5\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case 2:{\n" +
                "                System.out.println(\"Number is 2\");\n" +
                "                break;\n" +
                "            }\n" +
                "            default:\n" +
                "                System.out.println(\"Every other number\");    \n" +
                "        }\n" +
                "    }\n" +
                "}\n");

        assert_()
                .about(javaSource())
                .that(fileObject)
                .processedWith(underTest)
                .compilesWithoutError();
        assertFileExists("ProgramingConditionalConcepts.md");
        assertFileContains("ProgramingConditionalConcepts.md",
                "The document is going to focus on conditional behaviour",
                "## If Statement",
                "A standard if statement would perform an additional task ONLY", "void ifStatement(int number)",
                "## Switch Statements");
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
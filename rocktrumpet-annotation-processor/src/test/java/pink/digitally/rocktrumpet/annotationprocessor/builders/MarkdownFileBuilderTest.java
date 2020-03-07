package pink.digitally.rocktrumpet.annotationprocessor.builders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Markdown Builder should:")
class MarkdownFileBuilderTest {

    private MarkdownFileBuilder underTest;

    @BeforeEach
    void setUp() {
        underTest = new MarkdownFileBuilder();
    }

    @Test
    @DisplayName("add title as H1")
    void canBuildATitle(){
        final String titleBody = "This is a title";
        String result = underTest
                .title(titleBody)
                .build();

        assertEquals(String.format("# %s%n", titleBody), result);
    }

    @Test
    @DisplayName("add blockquotes")
    void blockquotes(){
        final String sampleBody = "Something good";
        String result = underTest
                .blockquote(sampleBody)
                .build();
        assertEquals(String.format("> %s%n", sampleBody), result);
    }
}
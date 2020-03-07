package pink.digitally.rocktrumpet.annotationprocessor.config;

import pink.digitally.rocktrumpet.annotationprocessor.builders.FileContentBuilder;
import pink.digitally.rocktrumpet.annotationprocessor.builders.MarkdownFileBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class RocktrumpetProperties {

    private static final String DOCS_PATH_KEY = "docs.path";
    private final Properties properties;

    //This is wrong
    public RocktrumpetProperties() {
        properties = new Properties();
        load();
    }

    private void load() {
        try (final InputStream resourceAsStream = RocktrumpetProperties.class
                .getResourceAsStream("/rocktrumpet.properties")) {

            if (resourceAsStream != null) {
                properties
                        .load(resourceAsStream);
                System.out.println("Properties loaded");
            }
        } catch (IOException e) {
            //This is wrong
            e.printStackTrace();
        }
    }

    public String getDocumentPath() {
        return properties.getProperty(DOCS_PATH_KEY,
                System.getProperty(DOCS_PATH_KEY,
                        System.getProperty("java.io.tmpdir")));
    }

    public Optional<String> getDocumentName() {
        return Optional.ofNullable(properties.getProperty("docs.name"));
    }

    public boolean createTableOfContents() {
        //This is really either "false" or true.
        // Basically create the table of contents by default
        return Boolean.parseBoolean(properties.getProperty("create.table.of.contents", "true"));
    }

    public FileContentBuilder getFileContentBuilder() {
        return new MarkdownFileBuilder();
    }
}

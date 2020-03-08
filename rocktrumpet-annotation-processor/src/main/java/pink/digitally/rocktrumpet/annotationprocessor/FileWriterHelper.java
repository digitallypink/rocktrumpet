package pink.digitally.rocktrumpet.annotationprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pink.digitally.rocktrumpet.annotationprocessor.handlers.PageDetails;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger("rocktrumpet");
    private FileWriterHelper() {
    }

    public static void writeFiles(String documentDirectory, PageDetails documentDetail) {
        try {
            File file = new File(documentDirectory);
            if (!file.exists()) {
                final boolean mkdirs = file.mkdirs();
                LOGGER.debug("Created Directory {}: {}", file.getAbsolutePath(), mkdirs);
            }
            final Path document = new File(file, documentDetail.getFileName().toString()).toPath();
            Files.write(document,
                    documentDetail.getFileContents().toString().getBytes(StandardCharsets.UTF_8));
            LOGGER.debug("Finished writing file, {}", document);
        } catch (IOException e) {
            LOGGER.error("Failed to write file, '{}'", documentDetail.getFileName(), e);
        }
    }
}

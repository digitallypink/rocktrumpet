package pink.digitally.rocktrumpet.annotationprocessor;

import pink.digitally.rocktrumpet.annotationprocessor.handlers.PageDetails;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileWriterHelper {
    private FileWriterHelper() {
    }

    public static void writeFiles(String documentDirectory, PageDetails documentDetail) {
        try {
            File file = new File(documentDirectory);
            if (!file.exists()) {
                //TODO log the success or the failure of this.
                file.mkdirs();
            }
            Files.write(new File(file, documentDetail.getFileName().toString()).toPath(),
                    documentDetail.getFileContents().toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            //TODO Log the error
        }
    }
}

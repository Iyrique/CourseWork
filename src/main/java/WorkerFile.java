import java.io.File;
import java.io.IOException;

public class WorkerFile {

    public static File getFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IOException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist");
        }
        return file;
    }
}

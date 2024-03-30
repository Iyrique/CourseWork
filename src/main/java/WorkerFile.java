import java.io.File;

public class WorkerFile {

    public static File getFile(String filePath) throws IllegalArgumentException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        return file;
    }
}

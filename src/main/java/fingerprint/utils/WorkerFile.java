package fingerprint.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public static File createFileCopy(String fileOrigin, String fileResult) throws IOException {
        File fileOr = getFile(fileOrigin);
        File fileRes = new File(fileResult);
        if (!fileRes.exists()) {
            fileRes.createNewFile();
        }
        FileInputStream inputStream = new FileInputStream(fileOr);
        FileOutputStream outputStream = new FileOutputStream(fileRes);
        byte[] buffer = new byte[(int) fileOr.length()];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
        System.out.println("Файл успешно скопирован.");
        return fileRes;
    }
}

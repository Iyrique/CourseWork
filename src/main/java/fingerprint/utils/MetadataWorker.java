package fingerprint.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetadataWorker {

    private static final String ATTRIBUTE_NAME = "Fingerprint";

    public static void hider(String pathFile, String value) throws IOException {
        Path filePath = Path.of(pathFile);
        BasicFileAttributeView view = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
        BasicFileAttributes attributes = view.readAttributes();
        FileTime currentTime = FileTime.fromMillis(new Date().getTime());
        UserDefinedFileAttributeView userView = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
        userView.write(ATTRIBUTE_NAME, ByteBuffer.wrap(value.getBytes()));
        view.setTimes(attributes.lastModifiedTime(), currentTime, null);
        System.out.println("Success");
    }

    public static List<String> extractor(String pathFile) throws IOException {
        Path filePath = Path.of(pathFile);
        UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
        List<String> attributesList = view.list();
        List<String> ans = new ArrayList<>();
        for (String attributeName : attributesList) {
            int size = view.size(attributeName);
            byte[] value = new byte[size];
            view.read(attributeName, ByteBuffer.wrap(value));
            if (attributeName.contains(ATTRIBUTE_NAME)) {
                ans.add(attributeName + ": " + new String(value));
            }
        }
        return ans;
    }
}

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.NullInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
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
        for (String attributeName : attributesList) {
            // Получаем значение каждого атрибута
            int size = view.size(attributeName);
            byte[] value = new byte[size];
            view.read(attributeName, ByteBuffer.wrap(value));
            if (!attributeName.contains(ATTRIBUTE_NAME)) {
                attributesList.remove(attributeName);
            }
        }
        return attributesList;
    }
}

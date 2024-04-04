package fingerprint.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    private static String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isDuplicate(String inputString, String hashedString, String HASHES_FILE_PATH) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HASHES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(inputString) || line.contains(hashedString)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void saveToTxtFile(String inputString, String hashedString, String HASHES_FILE_PATH) {
        try {
            File file = new File(HASHES_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!isDuplicate(inputString, hashedString, HASHES_FILE_PATH)) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("Input String: " + inputString + "\n");
                    writer.write("SHA-256 Hash: " + hashedString + "\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String starter(String input, String HASHES_FILE_PATH) {
        String hashedString = generateHash(input);
        saveToTxtFile(input, hashedString, HASHES_FILE_PATH);
        System.out.println("Hash generated and saved to file.");
        return hashedString;
    }

    public static String getInfo(String hash, String HASHES_FILE_PATH) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(HASHES_FILE_PATH));
        String line;
        String oldLine = reader.readLine();
        while ((line = reader.readLine()) != null) {
            if (line.contains(hash)) {
                return oldLine;
            }
            oldLine = line;
        }
        return null;
    }
}
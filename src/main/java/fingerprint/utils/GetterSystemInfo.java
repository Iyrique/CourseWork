package fingerprint.utils;

import fingerprint.view.MainFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class GetterSystemInfo {

    private static String INPUT_STRING = "Input String: ";

    public static String getComputerName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }


    public static String getEMAIL() {
        return System.getProperty("user.mail");
    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        return currentDate.toString();
    }

    public static String concatenator() throws UnknownHostException {
        String email = getEMAIL();
        if (email == null) {
            email = "-";
        }
        return getComputerName() + "~" + getUserName() + "~" + email + "~" + getCurrentDate();
    }

    public static String concatenator(String compName, String userName, String email, String additionalInfo) {
        if (email.contains(MainFrame.DEFAULT_STRING_EMAIL)) {
            email = "-";
        }
        if (additionalInfo.contains(MainFrame.DEFAULT_ADD_INFO)) {
            additionalInfo = "-";
        }
        return compName + "~" + userName + "~" + email + "~" + getCurrentDate() + "~" + additionalInfo;
    }

    public static String parserInfo(String str) {
        if (str.contains(INPUT_STRING)) {
            str = str.replaceAll(INPUT_STRING, "");
        }
        StringBuilder sb = new StringBuilder();
        String[] strArray = str.split("~");
        sb.append("Computer name: ");
        sb.append(strArray[0]);
        sb.append("\n");
        sb.append("Username: ");
        sb.append(strArray[1]);
        sb.append("\n");
        sb.append("email: ");
        sb.append(strArray[2]);
        sb.append("\n");
        sb.append("Date: ");
        sb.append(strArray[3]);
        sb.append("\n");
        sb.append("Additional info: ");
        sb.append(strArray[4]);
        return sb.toString();
    }

}

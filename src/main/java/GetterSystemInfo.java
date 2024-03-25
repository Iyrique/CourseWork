import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class GetterSystemInfo {

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

}

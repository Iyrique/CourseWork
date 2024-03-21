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

}

import java.net.UnknownHostException;
import java.util.Locale;

public class App {

    public static void main(String[] args) throws Exception {
        winMain();
        printer();
    }

    public static void winMain() throws Exception {
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame().setVisible(true);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void printer() {
        try {
            System.out.println("Имя компьютера: " + GetterSystemInfo.getComputerName());
            System.out.println("Имя пользователя: " + GetterSystemInfo.getUserName());

            String userEmail = GetterSystemInfo.getEMAIL();
            if (userEmail != null && !userEmail.isEmpty()) {
                System.out.println("Почта пользователя: " + userEmail);
            } else {
                System.out.println("Почта пользователя не найдена.");
            }

            System.out.println("Текущая дата и время: " + GetterSystemInfo.getCurrentDate());
        } catch (UnknownHostException e) {
            System.err.println("Ошибка получения информации о компьютере: " + e.getMessage());
        }
    }
}

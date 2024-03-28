import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class MainFrame extends JFrame {

    public static final String DEFAULT_STRING_EMAIL = "Enter your email here!";
    public static final String DEFAULT_ADD_INFO = "Enter additional information here!";

    private JPanel panelMain;
    private JPanel infoPanel;
    private JTextField compNameField;
    private JTextField userNameField;
    private JTextField emailField;
    private JTextArea addInfoField;
    private JButton generatorButton;

    public MainFrame() throws UnknownHostException {
        this.setTitle("Fingerprint");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(600, 400);
        initComponents();
        centerWindow();
        this.generatorButton.setSize(30,10);
        generatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compName = compNameField.getText();
                String userName = userNameField.getText();
                String email = emailField.getText();
                String additionalInfo = addInfoField.getText();
                String fullInfo = GetterSystemInfo.concatenator(compName, userName, email, additionalInfo);
                System.out.println(fullInfo);
                System.out.println(HashGenerator.starter(fullInfo));
            }
        });
    }

    private void initComponents() throws UnknownHostException {
        this.compNameField.setText(GetterSystemInfo.getComputerName());
        this.userNameField.setText(GetterSystemInfo.getUserName());
        String email = GetterSystemInfo.getEMAIL();
        if (email != null) {
            this.emailField.setText(GetterSystemInfo.getEMAIL());
        } else {
            this.emailField.setText(DEFAULT_STRING_EMAIL);
        }
        this.addInfoField.setText(DEFAULT_ADD_INFO);
    }

    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(centerX, centerY);
    }

}

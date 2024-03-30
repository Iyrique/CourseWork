import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.UnknownHostException;

public class MainFrame extends JFrame {

    public static final String DEFAULT_STRING_EMAIL = "Enter your email here!";
    public static final String DEFAULT_ADD_INFO = "Enter additional information here!";
    public static final String DEFAULT_FILE = "No file!(";

    private JPanel panelMain;
    private JPanel infoPanel;
    private JTextField compNameField;
    private JTextField userNameField;
    private JTextField emailField;
    private JTextArea addInfoField;
    private JButton generatorButton;
    private JTextField fileField;
    private JButton chooseFileButton;
    private JButton chooseSaveFileButton;
    private JTextField resultField;
    private JPanel filePanel;

    public MainFrame() throws UnknownHostException {
        this.setTitle("Fingerprint");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(600, 500);
        initComponents();
        centerWindow();
        this.generatorButton.setSize(30, 10);
        generatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkerFile()) {
                        File fileInput = WorkerFile.getFile(fileField.getText());
                        File fileOutput = WorkerFile.getFile(resultField.getText());
                        String compName = compNameField.getText();
                        String userName = userNameField.getText();
                        String email = emailField.getText();
                        String additionalInfo = addInfoField.getText();
                        String fullInfo = GetterSystemInfo.concatenator(compName, userName, email, additionalInfo);
                        System.out.println(fullInfo);
                        System.out.println(HashGenerator.starter(fullInfo));
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showConfirmDialog(null, ex.getMessage(), "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите файл");
                fileChooser.changeToParentDirectory();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileField.setText(selectedFile.toString());
                    if (resultField.getText().equals(DEFAULT_FILE)) {
                        resultField.setText(selectedFile.toString());
                    }
                }
            }
        });
        chooseSaveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите файл для сохранения");
                if (fileField.getText().equals(DEFAULT_FILE) && resultField.getText().equals(DEFAULT_FILE)) {
                    fileChooser.changeToParentDirectory();
                } else if (!resultField.getText().equals(DEFAULT_FILE)) {
                    fileChooser.setCurrentDirectory(new File(resultField.getText()));
                } else {
                    fileChooser.setCurrentDirectory(new File(fileField.getText()));
                }
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    resultField.setText(selectedFile.toString());
                }
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
        fileField.setText(DEFAULT_FILE);
        resultField.setText(DEFAULT_FILE);
    }

    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(centerX, centerY);
    }

    private boolean checkerFile() {
        if (fileField.getText().equals(DEFAULT_FILE) || resultField.getText().equals(DEFAULT_FILE)) {
            throw new IllegalArgumentException("File doesn't exist");
        }
        if (fileField.getText().isEmpty() || resultField.getText().isEmpty()) {
            throw new IllegalArgumentException("File path cannot empty");
        }
        return true;
    }

}

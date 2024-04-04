package fingerprint.view;

import fingerprint.utils.MetadataWorker;
import fingerprint.utils.GetterSystemInfo;
import fingerprint.utils.HashGenerator;
import fingerprint.utils.WorkerFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class MainFrame extends JFrame {

    public static final String DEFAULT_STRING_EMAIL = "Enter your email here!";
    public static final String DEFAULT_ADD_INFO = "Enter additional information here!";
    public static final String DEFAULT_FILE = "No file!(";
    public static final String DEFAULT_FINGERPRINT = "Fingerprint не найден!";
    private static final String HASHES_FILE_PATH = "src/main/resources/hash.txt";

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
    private JButton checkerButton;
    private JTextArea fingerprintPrinterArea;
    private JButton checkHashButton;
    private JTextField hashFileField;
    private JButton changeHashFileButton;

    public MainFrame() throws UnknownHostException {
        this.setTitle("Fingerprint");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(600, 900);
        initComponents();
        centerWindow();
        generatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkerFile()) {
                        File fileInput = WorkerFile.getFile(fileField.getText());
                        File fileOutput;
                        try {
                            fileOutput = WorkerFile.getFile(resultField.getText());
                        } catch (IOException ex) {
                            fileOutput = WorkerFile.createFileCopy(fileField.getText(), resultField.getText());
                        }
                        String compName = compNameField.getText();
                        String userName = userNameField.getText();
                        String email = emailField.getText();
                        String additionalInfo = addInfoField.getText();
                        String fullInfo = GetterSystemInfo.concatenator(compName, userName, email, additionalInfo);
                        System.out.println(fullInfo);
                        String hash = HashGenerator.starter(fullInfo, hashFileField.getText());
                        System.out.println(hash);
                        if (!fileOutput.equals(fileInput)) {
                            fileOutput = WorkerFile.createFileCopy(fileInput.getAbsolutePath(),
                                    fileOutput.getAbsolutePath());
                        }
                        MetadataWorker.hider(fileOutput.getAbsolutePath(), hash);
                    }
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(null, ex.getMessage(), "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите файл для чтения или проверки");
                fileChooser.changeToParentDirectory();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileField.setText(selectedFile.toString());
                    resultField.setText(selectedFile.toString());
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
        checkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkerFile()) {
                        File fileInput = WorkerFile.getFile(fileField.getText());
                        List<String> lst = MetadataWorker.extractor(fileInput.getAbsolutePath());
                        StringBuilder text = new StringBuilder();
                        if (!lst.isEmpty()) {
                            for (String el: lst) {
                                text.append(el);
                                text.append("\n");
                            }
                        } else {
                            text = new StringBuilder(DEFAULT_FINGERPRINT);
                        }
                        fingerprintPrinterArea.setText(text.toString());
                    }
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(null, ex.getMessage(), "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        changeHashFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите файл c хэшами");
                if (hashFileField.getText().isEmpty()) {
                    fileChooser.changeToParentDirectory();
                } else {
                    fileChooser.setCurrentDirectory(new File(hashFileField.getText()));
                }
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    hashFileField.setText(selectedFile.toString());
                }
            }
        });
        checkHashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = fingerprintPrinterArea.getText();
                if (!text.contains(DEFAULT_FINGERPRINT)) {
                    String[] textArr = text.split(": ");
                    try {
                        String hash = textArr[1].trim();
                        String concInfo = HashGenerator.getInfo(hash, hashFileField.getText());
                        text += "\n";
                        if (concInfo != null) {
                            text += GetterSystemInfo.parserInfo(concInfo);
                            fingerprintPrinterArea.setText(text);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(null, ex.getMessage(), "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, DEFAULT_FINGERPRINT, "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
        fingerprintPrinterArea.setText(DEFAULT_FINGERPRINT);
        fingerprintPrinterArea.setEditable(false);
        hashFileField.setText(HASHES_FILE_PATH);
    }

    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(centerX, centerY);
    }

    private boolean checkerFile() throws IOException {
        if (fileField.getText().equals(DEFAULT_FILE) || resultField.getText().equals(DEFAULT_FILE)) {
            throw new IOException("File doesn't exist");
        }
        if (fileField.getText().isEmpty() || resultField.getText().isEmpty()) {
            throw new IOException("File path cannot empty");
        }
        return true;
    }

}

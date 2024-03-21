import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel panelMain;

    public MainFrame() {
        this.setTitle("Fingerprint");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}

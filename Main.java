import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Control control = new Control();
            control.showLogin(); // เริ่มที่ GUI Login
        });
    }
}


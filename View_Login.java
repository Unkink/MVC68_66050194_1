import javax.swing.*;
import java.awt.*;

public class View_Login extends JFrame {
    private Control control;

    public View_Login(Control c) {
        this.control = c;

        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // เปิดกลางหน้าจอ
        setLayout(new GridLayout(5, 2, 5, 5));

        JLabel lblId = new JLabel("User ID:");
        JTextField txtId = new JTextField();
        JLabel lblPw = new JLabel("Password:");
        JPasswordField txtPw = new JPasswordField();

        JCheckBox chkAdmin = new JCheckBox("Login as Admin");

        JButton btnLogin = new JButton("Login");
        JButton btnExit = new JButton("Exit");

        add(lblId);
        add(txtId);
        add(lblPw);
        add(txtPw);
        add(new JLabel(""));
        add(chkAdmin);
        add(btnLogin);
        add(btnExit);

        // ปุ่ม Login
        btnLogin.addActionListener(e -> {
            String id = txtId.getText();
            String pw = new String(txtPw.getPassword());
            boolean isAdmin = chkAdmin.isSelected();

            if (control.login(id, pw, isAdmin)) {
                JOptionPane.showMessageDialog(this, "Login Success!");
                dispose();
                // ตรวจสอบว่าเป็น Admin หรือ Student
                if (isAdmin) {
                    control.showStudentView();  // **Admin → ไปที่หน้า View_Student**
                } else {
                    control.showProfile(id);  // Student → ไปที่หน้า View_Profile
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ปุ่ม Exit
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
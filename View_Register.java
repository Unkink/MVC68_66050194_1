import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View_Register extends JFrame {
    public View_Register(Control control, Model model, String studentId) {
        setTitle("Register Subject");
        setSize(400, 300);
        setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> subjectList = new JList<>(listModel);

        // แสดงวิชาที่นักเรียนยังไม่ได้ลง
        for (Model.Subject s : model.getAllSubjects()) {
            boolean already = model.getRegistered().stream()
                    .anyMatch(r -> r.studentId.equals(studentId) && r.subjectId.equals(s.id));
            if (!already && model.canRegister(studentId, s.id)) {
                listModel.addElement(s.id + " - " + s.name);
            }
        }

        // ปุ่มลงทะเบียน
        JButton btnRegister = new JButton("ลงทะเบียน");
        btnRegister.addActionListener(e -> {
            String selected = subjectList.getSelectedValue();
            if (selected != null) {
                String subjectId = selected.split(" - ")[0];
                model.registerSubject(studentId, subjectId);
                JOptionPane.showMessageDialog(this, "ลงทะเบียนสำเร็จ!");
                dispose();
                control.showProfile(studentId); // กลับไปหน้าโปรไฟล์
            }
        });

        // ปุ่มกลับ
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            control.showProfile(studentId); // กลับไปหน้าโปรไฟล์ของนักเรียน
            dispose(); // ปิดหน้าจอนี้
        });

        // เพิ่มปุ่ม Back และ Register
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        bottomPanel.add(btnRegister);

        add(new JScrollPane(subjectList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

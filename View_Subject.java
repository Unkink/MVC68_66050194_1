import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class View_Subject extends JFrame {
    public View_Subject(Control control, Model model, boolean isAdmin) {
        setTitle("Subjects");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table to display subjects
        JTable subjectTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Subject Code", "Subject Name"}, 0);
        subjectTable.setModel(tableModel);

        // Populate the table with subjects
        for (Model.Subject s : model.getAllSubjects()) {
            tableModel.addRow(new Object[]{s.id, s.name});
        }

        JScrollPane scrollPane = new JScrollPane(subjectTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel for Back and Update Grade
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Back button to go to View_Student (For Admin)
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            control.showStudentView(); // **กลับไปที่หน้า View_Student**
            dispose(); // Close current window
        });
        bottomPanel.add(btnBack);

        // Update Grade button (For Admin)
        JButton btnUpdateGrade = new JButton("Update Grade");
        btnUpdateGrade.addActionListener(e -> {
            int row = subjectTable.getSelectedRow();
            if (row >= 0) {
                String subjectId = subjectTable.getValueAt(row, 0).toString();
                if (isAdmin) {
                    // Admin is directed to View_Grading for the selected subject
                    control.showGrading(subjectId); // **ส่ง subjectId ไปยัง Control**
                    dispose(); // Close current window
                } else {
                    // Other logic for non-admin
                    control.showGrading(subjectId);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a subject first.");
            }
        });
        bottomPanel.add(btnUpdateGrade);

        // Add the bottom panel with buttons
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
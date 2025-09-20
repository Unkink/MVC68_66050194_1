import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class View_Grading extends JFrame {
    private Control control;
    private Model model;
    private String subjectId;
    private JTable gradeTable;
    private JLabel lblSubjectInfo;

    public View_Grading(Control control, Model model, String subjectId) {
        this.control = control;
        this.model = model;
        this.subjectId = subjectId;
        
        // Find the subject to get its name
        Model.Subject selectedSubject = model.getAllSubjects().stream()
                .filter(s -> s.id.equals(subjectId))
                .findFirst()
                .orElse(null);

        String subjectName = (selectedSubject != null) ? selectedSubject.name : "Unknown Subject";
        setTitle("Grading for " + subjectName);
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10)); // Add gaps for better spacing

        // Top Panel for subject info and count
        JPanel topPanel = new JPanel(new BorderLayout());
        
        List<Model.RegisteredSubject> registeredStudents = model.getRegistered().stream()
                .filter(r -> r.subjectId.equals(subjectId))
                .collect(Collectors.toList());
        
        lblSubjectInfo = new JLabel("Subject: " + subjectId + " - " + subjectName + " (" + registeredStudents.size() + " registered students)");
        lblSubjectInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubjectInfo.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(lblSubjectInfo, BorderLayout.CENTER);

        // Back button to the left
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            control.showSubject();
            dispose();
        });
        topPanel.add(btnBack, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // Table Model
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Student ID", "Student Name", "Grade"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only the Grade column is editable
            }
        };

        gradeTable = new JTable(tableModel);

        // Populate the table with registered students and their grades
        for (Model.RegisteredSubject r : registeredStudents) {
            Model.Student student = model.getAllStudents().stream()
                    .filter(s -> s.id.equals(r.studentId))
                    .findFirst()
                    .orElse(null);

            if (student != null) {
                tableModel.addRow(new Object[]{student.id, student.fname + " " + student.lname, r.grade});
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Submit button panel
        JPanel bottomPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit Grades");
        btnSubmit.addActionListener(e -> {
            for (int i = 0; i < gradeTable.getRowCount(); i++) {
                String studentIdInTable = gradeTable.getValueAt(i, 0).toString();
                String newGrade = (String) gradeTable.getValueAt(i, 2);
                model.gradeStudent(studentIdInTable, subjectId, newGrade); // Pass subjectId
            }
            JOptionPane.showMessageDialog(this, "Grades updated successfully!");
            control.showSubject(); // Go back to subject list after update
            dispose();
        });
        bottomPanel.add(btnSubmit);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;

public class View_Student extends JFrame {
    private Control control;
    private Model model;
    private JTable table;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnSortName, btnSortAge, btnUpdateGrade, btnSearch, btnBack;

    public View_Student(Control c, Model m) {
        this.control = c;
        this.model = m;
        setTitle("Student List (Admin)");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Toolbar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Set FlowLayout to align components to the left

        txtSearch = new JTextField(15);
        cbFilter = new JComboBox<>();
        populateSchoolFilter(); // เพิ่มโรงเรียนทั้งหมดลงใน dropdown
        btnSortName = new JButton("Sort by Name");
        btnSortAge = new JButton("Sort by Age");
        btnSearch = new JButton("Search");

        // Add components to topPanel
        topPanel.add(new JLabel("Search:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch); // ปุ่มค้นหา
        topPanel.add(new JLabel("School:"));
        topPanel.add(cbFilter);
        topPanel.add(btnSortName);
        topPanel.add(btnSortAge); // เพิ่มปุ่ม Sort by Age

        add(topPanel, BorderLayout.NORTH);

        // Table
        table = new JTable();
        refreshTable(model.getAllStudents());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button Panel for Back and Update Grade (Centered)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // จัดปุ่มให้กลาง

        // Back button to go to View_Login
        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            control.showLogin(); // **กลับไปที่หน้า View_Login**
            dispose(); // ปิดหน้าปัจจุบัน
        });
        bottomPanel.add(btnBack);

        // Update Grade button
        btnUpdateGrade = new JButton("Update Grade");
        btnUpdateGrade.addActionListener(e -> {
            // เมื่อกดปุ่ม Update Grade ในหน้า Student จะไปยังหน้า Subject
            control.showSubject();
            dispose();
        });
        bottomPanel.add(btnUpdateGrade);

        add(bottomPanel, BorderLayout.SOUTH);

        // Event: กดปุ่ม Sort by Name
        btnSortName.addActionListener(e -> {
            List<Model.Student> sortedList = model.getAllStudents().stream()
                    .sorted(Comparator.comparing(s -> s.fname + " " + s.lname)) // เรียงตามชื่อ
                    .collect(Collectors.toList());
            refreshTable(sortedList);
        });

        // Event: กดปุ่ม Sort by Age
        btnSortAge.addActionListener(e -> {
            List<Model.Student> sortedList = model.getAllStudents().stream()
                    .sorted(Comparator.comparing(s -> s.dob)) // เรียงตามวันเกิด (อายุ)
                    .collect(Collectors.toList());
            refreshTable(sortedList);
        });

        // Event: กรองตามโรงเรียน
        cbFilter.addActionListener(e -> {
            String selectedSchool = (String) cbFilter.getSelectedItem();
            List<Model.Student> filteredList = model.getAllStudents().stream()
                    .filter(s -> selectedSchool.equals("All") || s.school.equals(selectedSchool)) // กรองตามโรงเรียน
                    .collect(Collectors.toList());
            refreshTable(filteredList);
        });

        // Event: กดปุ่ม Search
        btnSearch.addActionListener(e -> {
            String searchTerm = txtSearch.getText().trim().toLowerCase();
            if (!searchTerm.isEmpty()) {
                List<Model.Student> filteredList = model.getAllStudents().stream()
                        .filter(s -> s.id.toLowerCase().contains(searchTerm) ||
                                (s.fname + " " + s.lname).toLowerCase().contains(searchTerm)) // ค้นหาทั้ง ID และ ชื่อ
                        .collect(Collectors.toList());
                refreshTable(filteredList);
            } else {
                // ถ้าไม่กรอกคำค้นหาให้แสดงข้อมูลทั้งหมด
                refreshTable(model.getAllStudents());
            }
        });

        setVisible(true);
    }

    private void populateSchoolFilter() {
        Set<String> schools = new TreeSet<>(); // ใช้ TreeSet เพื่อให้เรียงลำดับ
        for (Model.Student s : model.getAllStudents()) {
            // เพิ่มโรงเรียนที่ไม่เป็น null หรือช่องว่าง
            if (s.school != null && !s.school.trim().isEmpty()) {
                schools.add(s.school); // เพิ่มแต่ละโรงเรียนลงใน Set
            }
        }

        // Clear existing items
        cbFilter.removeAllItems();

        // เพิ่ม "All" ใน dropdown
        cbFilter.addItem("All");

        // เพิ่มโรงเรียนที่เรียงลำดับลงใน dropdown
        for (String school : schools) {
            cbFilter.addItem(school);
        }
    }

    private void refreshTable(List<Model.Student> list) {
        String[] cols = { "ID", "Name", "School", "Email" };
        DefaultTableModel dt = new DefaultTableModel(cols, 0);
        for (Model.Student s : list) {
            if (!s.isAdmin) {
                dt.addRow(new Object[] { s.id, s.fname + " " + s.lname, s.school, s.email });
            }
        }
        table.setModel(dt);
    }
}
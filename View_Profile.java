import javax.swing.*;
import java.awt.*;

public class View_Profile extends JFrame {
    public View_Profile(Control control, Model model, String studentId, boolean isAdmin) {
        setTitle("Student Profile");
        setSize(400, 500);  // ปรับขนาดหน้าต่างให้ใหญ่ขึ้น
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        // ดึงข้อมูลนักเรียน
        for (Model.Student s : model.getAllStudents()) {
            if (s.id.equals(studentId)) {
                area.append("ชื่อ: " + s.prefix + s.fname + " " + s.lname + "\n");
                area.append("โรงเรียน: " + s.school + "\n");
                area.append("Email: " + s.email + "\n");
            }
        }

        // แสดงรายละเอียดหลักสูตรเพียงครั้งเดียว
        for (Model.Student s : model.getAllStudents()) {
            if (s.id.equals(studentId)) {
                // ดึงข้อมูลจาก SubjectStructure ที่นักเรียนลงทะเบียน
                for (Model.SubjectStructure ss : model.getAllSubjectStructures()) {
                    if (ss.courseId.equals(s.courseId)) {
                        // แสดงข้อมูลหลักสูตรและรายวิชาที่ลงทะเบียนในบรรทัดเดียวกัน
                        area.append("\nหลักสูตร: " + ss.courseId + " - " + ss.courseName + "\n");
                        area.append("ชื่อภาควิชา: " + ss.dept + "\n");
                        area.append("รหัสวิชาที่บังคับ: " + ss.subjectId + "\n");
                        area.append("เทอมที่เปิดสอน: " + (ss.term == 1 ? "เทอม 1" : "เทอม 2"));

                        // แสดงรายวิชาที่นักเรียนลงทะเบียนแล้ว
                        area.append("\nรายวิชาที่ลงทะเบียนแล้ว:\n");
                        for (Model.RegisteredSubject r : model.getRegistered()) {
                            if (r.studentId.equals(studentId) && r.subjectId.equals(ss.subjectId)) {
                                // ตรวจสอบว่าเกรดยังไม่ออก
                                String grade = r.grade.isEmpty() ? "ระหว่างรอดำเนินการ" : r.grade;
                                area.append("- " + r.subjectId + " เกรด: " + grade + "\n");
                            }
                        }
                    }
                }
                break; // หยุดหลังจากแสดงข้อมูลหลักสูตรของนักเรียนคนนั้นแล้ว
            }
        }

        // Scrollable text area
        add(new JScrollPane(area), BorderLayout.CENTER);

        // ปุ่ม Back และ Update Grade สำหรับ Admin / Student
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // จัดปุ่มให้ซ้ายสุด

        JButton btnBack = new JButton("Back");
        JButton btnUpdateGrade = new JButton("Update Grade");

        // สำหรับ Admin
        if (isAdmin) {
            btnBack.addActionListener(e -> {
                control.showStudentView(); // กลับไปที่ View_Student
                dispose(); // ปิดหน้าปัจจุบัน
            });
            btnUpdateGrade.addActionListener(e -> {
                control.showGrading(studentId); // ไปที่หน้ากรอกเกรด
                dispose(); // ปิดหน้าปัจจุบัน
            });
            bottomPanel.add(btnBack);  // เพิ่มปุ่ม Back
            bottomPanel.add(btnUpdateGrade);  // เพิ่มปุ่ม Update Grade
        } else { // สำหรับ Student
            btnBack.addActionListener(e -> {
                control.showLogin(); // กลับไปที่ View_Login
                dispose(); // ปิดหน้าปัจจุบัน
            });
            bottomPanel.add(btnBack);  // ปุ่ม Back สำหรับ Student
            JButton btnRegister = new JButton("Register Subjects");
            btnRegister.addActionListener(e -> {
                control.showRegister(studentId); // ไปที่หน้าลงทะเบียนเรียน
                dispose(); // ปิดหน้าปัจจุบัน
            });
            bottomPanel.add(btnRegister); // เพิ่มปุ่ม Register Subjects สำหรับ Student
        }

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
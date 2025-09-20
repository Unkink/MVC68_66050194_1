import java.util.*;

public class Model {
    // ===== จำลองฐานข้อมูล =====
    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<SubjectStructure> structures = new ArrayList<>();
    private List<RegisteredSubject> registered = new ArrayList<>();

    public Model() {
        seedData(); // เตรียมข้อมูลตัวอย่าง
    }

    // ---------- Classes ----------
    public static class Student {
        String id, prefix, fname, lname, school, email, courseId, password;
        Date dob;
        boolean isAdmin;

        public Student(String id, String prefix, String fname, String lname,
                Date dob, String school, String email,
                String courseId, String password, boolean isAdmin) {
            this.id = id;
            this.prefix = prefix;
            this.fname = fname;
            this.lname = lname;
            this.dob = dob;
            this.school = school;
            this.email = email;
            this.courseId = courseId;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }

    public static class Subject {
        String id, name, teacher, prereq;
        int credit;

        public Subject(String id, String name, int credit, String teacher, String prereq) {
            this.id = id;
            this.name = name;
            this.credit = credit;
            this.teacher = teacher;
            this.prereq = prereq;
        }
    }

    public static class RegisteredSubject {
        String studentId, subjectId;
        String grade; // A, B+, ...

        public RegisteredSubject(String studentId, String subjectId, String grade) {
            this.studentId = studentId;
            this.subjectId = subjectId;
            this.grade = grade;
        }
    }

    public static class SubjectStructure {
        String courseId, courseName, dept, subjectId;
        int term;

        public SubjectStructure(String courseId, String courseName,
                String dept, String subjectId, int term) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.dept = dept;
            this.subjectId = subjectId;
            this.term = term;
        }
    }

    // ---------- Methods ----------
    private void seedData() {
        // ตัวอย่างข้อมูลนักเรียนและวิชา
        students.add(new Student("admin", "-", "Admin", "System", new Date(), "", "admin@mail.com", "", "admin", true));
        students.add(new Student("69000001", "นาย", "สมชาย", "ใจดี", new Date(2006 - 1900, 5, 12), "รร.เอ",
                "somchai1@mail.com", "10000001", "1111", false));
        students.add(new Student("69000002", "นางสาว", "ศิริพร", "งามดี", new Date(2007 - 1900, 3, 25), "รร.บี",
                "siriporn@mail.com", "10000001", "2222", false));
        students.add(new Student("69000003", "นาย", "ณัฐวุฒิ", "มีสุข", new Date(2005 - 1900, 11, 2), "รร.ซี",
                "natthawut@mail.com", "10000002", "3333", false));
        students.add(new Student("69000004", "นางสาว", "กมลวรรณ", "ใจงาม", new Date(2006 - 1900, 7, 19), "รร.ดี",
                "kamonwan@mail.com", "10000002", "4444", false));
        students.add(new Student("69000005", "นาย", "อนันต์", "แสงทอง", new Date(2007 - 1900, 1, 10), "รร.อี",
                "anan@mail.com", "10000001", "5555", false));
        students.add(new Student("69000006", "นางสาว", "วราภรณ์", "มีชัย", new Date(2005 - 1900, 9, 23), "รร.เอฟ",
                "waraporn@mail.com", "10000002", "6666", false));
        students.add(new Student("69000007", "นาย", "ปริญญา", "ตั้งใจ", new Date(2006 - 1900, 4, 8), "รร.จี",
                "parinya@mail.com", "10000001", "7777", false));
        students.add(new Student("69000008", "นางสาว", "พิมพ์ใจ", "สดใส", new Date(2007 - 1900, 12, 1), "รร.เอช",
                "pimjai@mail.com", "10000001", "8888", false));
        students.add(new Student("69000009", "นาย", "ธีรภัทร", "มั่นคง", new Date(2006 - 1900, 6, 15), "รร.ไอ",
                "teerapat@mail.com", "10000002", "9999", false));
        students.add(new Student("69000010", "นางสาว", "อัญชลี", "รุ่งเรือง", new Date(2005 - 1900, 2, 27), "รร.เจ",
                "anchalee@mail.com", "10000002", "0000", false));

        // วิชา
        subjects.add(new Subject("05500001", "Programming I", 3, "ดร.กิตติ", ""));
        subjects.add(new Subject("05500002", "Programming II", 3, "ดร.กิตติ", "05500001"));
        subjects.add(new Subject("05500003", "Data Structures", 3, "ผศ.สุรีย์", "05500002"));
        subjects.add(new Subject("05500004", "Computer Systems", 3, "รศ.ประวิทย์", ""));
        subjects.add(new Subject("05500005", "Database Systems", 3, "อ.สมชาย", "05500002"));

        subjects.add(new Subject("90690001", "General English I", 3, "อ.สมหญิง", ""));
        subjects.add(new Subject("90690002", "General English II", 3, "อ.สมหญิง", "90690001"));
        subjects.add(new Subject("90690003", "Mathematics for IT", 3, "อ.วรพงษ์", ""));
        subjects.add(new Subject("90690004", "Introduction to IT", 3, "อ.เกียรติ", ""));
        subjects.add(new Subject("90690005", "Digital Literacy", 3, "อ.จารุวรรณ", ""));

        // ลงทะเบียน
        registered.add(new RegisteredSubject("69000001", "05500001", "A"));
        registered.add(new RegisteredSubject("69000002", "05500001", "B+"));

        // ----- Subject Structure (หลักสูตร) -----
        // สำหรับหลักสูตร CS (10000001)
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500001", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500002", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500003", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500004", 2)); // เทอม 2
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500005", 2)); // เทอม 2
        structures.add(new SubjectStructure("10000001", "CS", "วิทยาการคอมพิวเตอร์", "05500006", 2)); // เทอม 2

        // สำหรับหลักสูตร IT (10000002)
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690001", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690002", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690003", 1)); // เทอม 1
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690004", 2)); // เทอม 2
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690005", 2)); // เทอม 2
        structures.add(new SubjectStructure("10000002", "IT", "เทคโนโลยีสารสนเทศ", "90690006", 2)); // เทอม 2
    }

    // ---------- Methods ----------

    // ฟังก์ชันสำหรับกรองนักเรียนที่ลงทะเบียนวิชาหนึ่ง
    public List<Student> getStudentsBySubjectId(String subjectId) {
        List<Student> studentsForSubject = new ArrayList<>();
        for (RegisteredSubject r : registered) {
            if (r.subjectId.equals(subjectId)) {
                for (Student s : students) {
                    if (s.id.equals(r.studentId)) {
                        studentsForSubject.add(s);
                    }
                }
            }
        }
        return studentsForSubject;
    }

    // ฟังก์ชันที่ให้ข้อมูลเกี่ยวกับ SubjectStructure ตาม courseId
    public List<SubjectStructure> getSubjectStructureByCourseId(String courseId) {
        List<SubjectStructure> courseStructures = new ArrayList<>();
        for (SubjectStructure ss : structures) {
            if (ss.courseId.equals(courseId)) {
                courseStructures.add(ss);
            }
        }
        return courseStructures;
    }

    // ฟังก์ชันที่จะคืนข้อมูล Subject โดยเชื่อมโยงกับ Course Id
    public List<Subject> getSubjectsByCourseId(String courseId) {
        List<Subject> subjectList = new ArrayList<>();
        // ใช้ข้อมูลจาก SubjectStructure เพื่อเชื่อมโยง
        for (SubjectStructure ss : structures) {
            if (ss.courseId.equals(courseId)) {
                for (Subject subj : subjects) {
                    if (subj.id.equals(ss.subjectId)) {
                        subjectList.add(subj);
                    }
                }
            }
        }
        return subjectList;
    }

    // ---------------- Getters ----------------
    public List<Student> getAllStudents() {
        return students;
    }

    public List<Subject> getAllSubjects() {
        return subjects;
    }

    public List<RegisteredSubject> getRegistered() {
        return registered;
    }

    public List<SubjectStructure> getAllSubjectStructures() {
        return structures;
    }

    public boolean authenticate(String userId, String password, boolean isAdmin) {
        if ("admin".equals(userId) && "admin".equals(password) && isAdmin) {
            return true; // ให้ Admin เข้าได้เลย
        }

        // ใช้ Iterator เพื่อวนลูปใน list students
        Iterator<Student> iterator = this.students.iterator();

        Student student;
        while (iterator.hasNext()) {
            student = iterator.next(); // ดึงข้อมูลของนักเรียนทีละคน
            // เช็คว่า id, password, และ isAdmin ตรงกับที่กรอกมาในแบบฟอร์มหรือไม่
            if (student.id.equals(userId) && student.password.equals(password) && student.isAdmin == isAdmin) {
                return true; // ถ้าตรงกันทั้งหมดจะ return true
            }
        }
        return false; // ถ้าไม่มีการตรงกันเลย return false
    }

    // เพิ่มฟังก์ชันนี้ใน Model.java
    public void gradeStudent(String studentId, String subjectId, String grade) {
            // หาวิชาที่ตรงกับ studentId และ subjectId
            for (RegisteredSubject r : registered) {
                if (r.studentId.equals(studentId) && r.subjectId.equals(subjectId)) {
                    r.grade = grade; // อัปเดตเกรด
                    break;
                }
        }
    }
    // ฟังก์ชันตรวจสอบว่านักเรียนสามารถลงทะเบียนวิชาได้หรือไม่
    public boolean canRegister(String studentId, String subjectId) {
        // เช็คว่านักเรียนลงทะเบียนวิชานี้ไปแล้วหรือยัง
        for (RegisteredSubject r : registered) {
            if (r.studentId.equals(studentId) && r.subjectId.equals(subjectId)) {
                return false; // ถ้านักเรียนลงทะเบียนวิชานี้ไปแล้วไม่สามารถลงทะเบียนซ้ำได้
            }
        }
        return true; // ถ้ายังไม่ได้ลงทะเบียน สามารถลงทะเบียนได้
    }

    // ฟังก์ชันสำหรับลงทะเบียนวิชา
    public void registerSubject(String studentId, String subjectId) {
        // ตรวจสอบก่อนว่านักเรียนสามารถลงทะเบียนได้หรือไม่
        if (canRegister(studentId, subjectId)) {
            // ถ้ายังไม่ได้ลงทะเบียน ให้เพิ่มการลงทะเบียนใหม่
            registered.add(new RegisteredSubject(studentId, subjectId, ""));
        } else {
            // ถ้านักเรียนลงทะเบียนไปแล้ว
            System.out.println("Student is already registered for this subject.");
        }

    }

}
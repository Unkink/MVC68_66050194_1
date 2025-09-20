public class Control {
    private Model model;
    private boolean isAdmin; // เก็บสถานะ Admin/Student
    private View_Subject viewSubject; // ประกาศตัวแปร View_Subject
    private View_Grading viewGrading; // ประกาศตัวแปร View_Grading

    public Control() {
        model = new Model();
    }

    // ---------------- Authentication ----------------
    public boolean login(String userId, String password, boolean isAdmin) {
        this.isAdmin = isAdmin; // เก็บสถานะ Admin
        return model.authenticate(userId, password, isAdmin);
    }

    // ---------------- Show Views ----------------
    public void showLogin() {
        new View_Login(this);
    }

    public void showStudentView() {
        new View_Student(this, model);
    }

    public void showProfile(String studentId) {
        new View_Profile(this, model, studentId, isAdmin); // ส่ง isAdmin ไปที่ View_Profile
    }

    public void showRegister(String studentId) {
        new View_Register(this, model, studentId);
    }

    // แก้ไข showSubject ให้ส่ง isAdmin ไปที่ View_Subject
    public void showSubject() {
        new View_Subject(this, model, isAdmin); 
    }

    public void showGrading(String subjectId) {
        new View_Grading(this, model, subjectId); // สร้างหน้าสำหรับกรอกเกรด
    }

    // ---------------- Model Accessors ----------------
    public Model getModel() {
        return model;
    }
}

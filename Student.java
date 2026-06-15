public class Student extends User {
    private String matricNo;
    public Student(String name, String matricNo) {
        super(name);
        this.matricNo = matricNo;
    }
    @Override
    public String getUniqueId() { return matricNo; }
    @Override
    public String getUserDetails() { return "Student: " + name + " (Matric: " + matricNo + ")"; }
} 

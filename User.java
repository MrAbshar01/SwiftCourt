abstract class User {
    protected String name;
    public User(String name) { this.name = name; }
    public String getName() { return name; }
    public abstract String getUniqueId();
    public abstract String getUserDetails();
}

class Student extends User {
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

class Staff extends User {
    private String staffId;
    public Staff(String name, String staffId) {
        super(name);
        this.staffId = staffId;
    }
    @Override
    public String getUniqueId() { return staffId; }
    @Override
    public String getUserDetails() { return "Staff: " + name + " (ID: " + staffId + ")"; }
}

abstract class Facility {
    protected String facilityId;
    protected String name;
    protected double baseRate;
    protected String status; 
    protected int courtLimit;

    public Facility(String facilityId, String name, double baseRate) {
        this.facilityId = facilityId;
        this.name = name;
        this.baseRate = baseRate;
        this.status = "Available";
        this.courtLimit = 1;
    }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public abstract double calculateBookingFee(int hours);
}

class IndoorSport extends Facility {
    private boolean hasAC;
    public IndoorSport(String facilityId, String name, double baseRate, boolean hasAC) {
        super(facilityId, name, baseRate);
        this.hasAC = hasAC;
    }
    @Override
    public double calculateBookingFee(int hours) {
        return (baseRate * hours) + (hasAC ? (15.0 * hours) : 0);
    }
}

class OutdoorSport extends Facility {
    private double weatherInsuranceFee;
    public OutdoorSport(String facilityId, String name, double baseRate, double weatherInsuranceFee) {
        super(facilityId, name, baseRate);
        this.weatherInsuranceFee = weatherInsuranceFee;
    }
    @Override
    public double calculateBookingFee(int hours) {
        return (baseRate * hours) + weatherInsuranceFee;
    }
}
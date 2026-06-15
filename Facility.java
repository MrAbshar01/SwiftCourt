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

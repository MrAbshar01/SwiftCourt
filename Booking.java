public class Booking {
    private String bookingId;
    private User user; 
    private Facility facility;
    private int durationHours;
    private Penalty penalty; 

    public Booking(String bookingId, User user, Facility facility, int durationHours) {
        this.bookingId = bookingId;
        this.user = user;
        this.facility = facility;
        this.durationHours = durationHours;
        this.penalty = null; 
    }

    // This method now correctly expects 3 arguments to process the active system reporter!
    public String issuePenalty(User reporter, String description, double fineInput) {
        this.facility.setStatus("Under Maintenance");
        
        if (reporter instanceof Staff) {
            this.penalty = new Penalty("P-" + bookingId, description, fineInput);
            return "!!! STAFF DAMAGE ADVISORY FILED !!!\n" +
                   "Booking Affected: " + this.bookingId + "\n" +
                   "Offending User: " + this.user.getName() + " (" + this.user.getUniqueId() + ")\n" +
                   "Reported By Staff: " + reporter.getName() + "\n" +
                   "Fine Balance Issued: RM " + fineInput + "\n" +
                   "Reason: " + description + "\n" +
                   "Facility shifted to offline maintenance arrays.\n";
        } else {
            this.penalty = new Penalty("P-" + bookingId, description, 0.0);
            return "!!! STUDENT COMPLAINT FILED !!!\n" +
                   "Booking Affected: " + this.bookingId + "\n" +
                   "Offending User: " + this.user.getName() + " (" + this.user.getUniqueId() + ")\n" +
                   "Reported By Student: " + reporter.getName() + "\n" +
                   "Notice: Student complaints carry RM 0 fine (Lacks Admin Authorization).\n" +
                   "Reason: " + description + "\n" +
                   "Facility shifted to offline maintenance arrays.\n";
        }
    }

    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Facility getFacility() { return facility; }
    public Penalty getPenalty() { return penalty; }
}
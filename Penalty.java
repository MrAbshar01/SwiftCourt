public class Penalty {
    private String penaltyId;
    private String issueDescription;
    private double fineAmount;
    private boolean isPaid;

    public Penalty(String penaltyId, String issueDescription, double fineAmount) {
        this.penaltyId = penaltyId;
        this.issueDescription = issueDescription;
        this.fineAmount = fineAmount;
        this.isPaid = (fineAmount == 0);
    }
    public double getFineAmount() { return fineAmount; }
    public String getDescription() { return issueDescription; }
}
public class IndoorSport extends Facility {
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

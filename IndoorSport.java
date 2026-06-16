public class IndoorSport extends Facility {
    private boolean hasAC;
    public IndoorSport(String facilityId, String name, double baseRate) {
        super(facilityId, name, baseRate);
    }
    @Override
    public double calculateBookingFee(int hours) {
        return (baseRate * hours);
    }
}

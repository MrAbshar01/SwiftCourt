public class OutdoorSport extends Facility {
    private double weatherInsuranceFee;
    public OutdoorSport(String facilityId, String name, double baseRate) {
        super(facilityId, name, baseRate);
    }
    @Override
    public double calculateBookingFee(int hours) {
        return (baseRate * hours);
    }
}

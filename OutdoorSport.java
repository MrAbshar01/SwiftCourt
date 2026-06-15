public class OutdoorSport extends Facility {
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

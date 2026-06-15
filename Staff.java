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

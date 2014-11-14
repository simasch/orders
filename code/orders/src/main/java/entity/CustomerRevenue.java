package entity;

public class CustomerRevenue {

    private final String customerName;
    private final double revenue;

    public CustomerRevenue(String customerName, double revenue) {
        this.customerName = customerName;
        this.revenue = revenue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getRevenue() {
        return revenue;
    }

}

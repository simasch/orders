package control;

public class CustomerInfoDTO {

    private final String customerName;
    private final double revenue;

    public CustomerInfoDTO(String customerName, double revenue) {
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

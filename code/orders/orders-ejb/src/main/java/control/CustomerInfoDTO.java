package control;

public class CustomerInfoDTO {

    private final Long id;
    private final String customerName;
    private final Integer numberOfOrders;
    private final double revenue;

    public CustomerInfoDTO(Long id, String customerName, Integer numberOfOrders, double revenue) {
        this.id = id;
        this.customerName = customerName;
        this.numberOfOrders = numberOfOrders;
        this.revenue = revenue;
    }

    public CustomerInfoDTO(Long id, String customerName, Long numberOfOrders, double revenue) {
        this.id = id;
        this.customerName = customerName;
        this.numberOfOrders = numberOfOrders.intValue();
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public double getRevenue() {
        return revenue;
    }

}

package entity;

public class CustomerInfoDTO {

    private final Long id;
    private final String name;
    private final double revenue;

    public CustomerInfoDTO(Long id, String name, double revenue) {
        this.id = id;
        this.name = name;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRevenue() {
        return revenue;
    }

}

package entity;

public class CustomerInfoDTO {

    private final Long id;
    private final String lastname;
    private final String firstname;
    private final double revenue;

    public CustomerInfoDTO(Long id, String lastname, String firstname, double revenue) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public double getRevenue() {
        return revenue;
    }

}

package boundry;

import control.CustomerService;
import entity.Customer;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerBean implements Serializable {

    @EJB
    private CustomerService customerService;
    private List<Customer> customers;

    private String searchText = "";

    public void search() {
        customers = customerService.getCustomers();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}

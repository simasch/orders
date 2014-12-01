package boundry;

import control.CustomerInfoDTO;
import control.CustomerService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerV2Bean implements Serializable {

    @EJB
    private CustomerService customerService;
    private List<CustomerInfoDTO> customers;

    private String searchText = "";

    public void search() {
        customers = customerService.getCustomersWithConstructorExpression();
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerInfoDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerInfoDTO> customers) {
        this.customers = customers;
    }

  
    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}

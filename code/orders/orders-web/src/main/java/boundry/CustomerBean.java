package boundry;

import control.CustomerService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CustomerBean implements Serializable {

    @EJB
    private CustomerService customerService;
    private List customers;

    private String searchText = "";

    public void searchV1() {
        customers = customerService.getCustomers();
    }

    public void searchV2() {
        customers = customerService.getCustomersWithConstructorExpression();
    }
    
    public void clear() {
        customers = null;
    }

    public List getCustomers() {
        return customers;
    }

    public void setCustomers(List customers) {
        this.customers = customers;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getNumberOfCustomers() {
        if (customers == null) {
            return 0;
        } else {
            return customers.size();
        }
    }
}

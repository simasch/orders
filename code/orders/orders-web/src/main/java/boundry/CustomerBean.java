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

    private List customers;
    private Customer customer;

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
    
    public String edit(Customer customer) {
        this.customer = customer;
        
        return "customer_edit.xhtml";
    }

    public String save() {
        customerService.save(customer);
        
        return "customer_list.xhtml";
    }

    public String back() {
        customers = null;
        
        return "index.xhtml";
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}

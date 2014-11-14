package control;

import entity.Customer;
import entity.Order;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class RevenueCalculator {

    private final EntityManager em;

    public RevenueCalculator(EntityManager em) {
        this.em = em;
    }

    public double calculateRevenue() {
        TypedQuery q = em.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = q.getResultList();

        double total = 0d;
        
        for (Customer c : customers) {
            for (Order o : c.getOrders()) {
                total += o.getPrice();
            }
        }
        
        return total;
    }
}

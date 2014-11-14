package control;

import entity.Customer;
import entity.Order;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class RevenueCalculator {

    private final EntityManager em;

    public RevenueCalculator(EntityManager em) {
        this.em = em;
    }

    public double calculateRevenueNaive() {
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
    
    public double calculateRevenueConstructorExpression() {
        Query q = em.createQuery("SELECT SUM(i.product.price) FROM Customer c JOIN c.orders o JOIN o.items i");
        Double total = (Double) q.getSingleResult();
        return total;
    }
}

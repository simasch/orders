package control;

import entity.CustomerInfoDTO;
import entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class CustomerService {

    @PersistenceContext
    protected EntityManager em;

    public List<Customer> getCustomers() {
        TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c ORDER BY c.name", Customer.class);

        return q.getResultList();
    }

    public List<CustomerInfoDTO> getCustomersWithConstructorExpression() {
        Query q = em.createQuery(
                "SELECT NEW entity.CustomerInfoDTO(c.id, c.name, SUM(i.product.price))"
                + " FROM Customer c JOIN c.orders o JOIN o.items i GROUP BY c.id, c.name ORDER BY c.name");

        return q.getResultList();
    }

}

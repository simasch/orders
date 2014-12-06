package control;

import entity.CustomerInfoDTO;
import entity.Customer;
import entity.Order;
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
        TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c ORDER BY c.lastname, c.firstname", Customer.class);

        List<Customer> list = q.getResultList();

// Only for Hibernate and Client/Server Apps
//
//        for (Customer customer : list) {
//            for (Order order : customer.getOrders()) {
//                order.getItems().iterator();
//            }
//        }

        return list;
    }

    public List<CustomerInfoDTO> getCustomersWithConstructorExpression() {
        Query q = em.createQuery(
                "SELECT NEW entity.CustomerInfoDTO(c.id, c.lastname, c.firstname, SUM(i.product.price)) "
                + "FROM Customer c JOIN c.orders o JOIN o.items i "
                + "GROUP BY c.id, c.lastname, c.firstname "
                + "ORDER BY c.lastname, c.firstname");

        return q.getResultList();
    }
    
    public <T>T save(T t) {
        return em.merge(t);
    }

}

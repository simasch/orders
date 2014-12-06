package control;

import entity.Customer;
import entity.Customer_;
import entity.Order_;
import entity.CustomerInfoDTO;
import entity.Order;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.qlrm.mapper.JpaResultMapper;

public class OrderTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static CustomerService customerService;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("orders-test");
        em = emf.createEntityManager();

        customerService = new CustomerService();
        customerService.em = em;
    }

    @AfterClass
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Test @Ignore
    public void getCustomersWithEntityGraph() {
        TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c ORDER BY c.lastname, c.firstname", Customer.class);

        EntityGraph fetchDeep = em.createEntityGraph("Customer.fetchDeep");
        q.setHint("javax.persistence.fetchgraph", fetchDeep);

        List<Customer> list = q.getResultList();

        Assert.assertNotNull(list);
    }

    @Test
    public void calculateRevenueConstructorExpression() {
        List<CustomerInfoDTO> list = customerService.getCustomersWithConstructorExpression();

        Assert.assertNotNull(list);
    }

    @Test
    public void calculateRevenueConstructorResult() {
        Query q = em.createNativeQuery(
                "SELECT C.ID, C.LASTNAME, C.FIRSTNAME ,SUM(P.PRICE) AS REVENUE "
                + "FROM CUSTOMERS C "
                + "JOIN ORDERS O ON O.CUSTOMER_ID = C.ID "
                + "JOIN ORDERITEMS I ON I.ORDER_ID = O.ID "
                + "JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID "
                + "GROUP BY C.ID, C.LASTNAME, C.FIRSTNAME ORDER BY C.LASTNAME, C.FIRSTNAME",
                "CustomerInfoDTO");

        List<CustomerInfoDTO> list = q.getResultList();

        Assert.assertNotNull(list);
    }

    @Test
    public void calculateRevenueQlrm() {
        Query q = em.createNativeQuery(
                "SELECT C.ID, C.LASTNAME, C.FIRSTNAME, SUM(P.PRICE) "
                + "FROM CUSTOMERS C JOIN ORDERS O ON O.CUSTOMER_ID = C.ID "
                + "JOIN ORDERITEMS I ON I.ORDER_ID = O.ID JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID "
                + "GROUP BY C.ID, C.LASTNAME, C.FIRSTNAME ORDER BY C.LASTNAME, C.FIRSTNAME");

        JpaResultMapper mapper = new JpaResultMapper();

        List<CustomerInfoDTO> list = mapper.list(q, CustomerInfoDTO.class);

        Assert.assertNotNull(list);
    }

    @Test
    @Ignore
    public void calculateRevenueConstructorExpressionJoinOn() {
        Query q = em.createQuery(
                "SELECT "
                + "NEW entity.CustomerInfoDTO(c.id, c.lastname, c.firstname, SUM(i.product.price)) "
                + "FROM Customer c "
                + "JOIN c.orders o ON o.customer_id = c.id "
                + "JOIN o.items i "
                + "GROUP BY c.id, c.lastname, c.firstname "
                + "ORDER BY c.lastname, c.firstname");

        List<CustomerInfoDTO> list = q.getResultList();

        Assert.assertNotNull(list);
    }

}

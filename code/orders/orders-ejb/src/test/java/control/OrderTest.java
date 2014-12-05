package control;

import entity.CustomerInfoDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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

    @Test
    public void calculateRevenueConstructorExpression() {
        List<CustomerInfoDTO> list = customerService.getCustomersWithConstructorExpression();

        Assert.assertNotNull(list);
    }

    @Test
    public void calculateRevenueConstructorResult() {
        Query q = em.createNativeQuery(
                "SELECT C.ID, C.NAME, SUM(P.PRICE) AS REVENUE "
                + "FROM CUSTOMERS C "
                + "JOIN ORDERS O ON O.CUSTOMER_ID = C.ID "
                + "JOIN ORDERITEMS I ON I.ORDER_ID = O.ID "
                + "JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID "
                + "GROUP BY C.ID, C.NAME ORDER BY C.NAME",
                "CustomerInfoDTO");
        
        List<CustomerInfoDTO> list = q.getResultList();

        Assert.assertNotNull(list);
    }

    @Test
    public void calculateRevenueQlrm() {
        Query q = em.createNativeQuery(
                "SELECT C.ID, C.NAME, SUM(P.PRICE) "
                + "FROM CUSTOMERS C JOIN ORDERS O ON O.CUSTOMER_ID = C.ID "
                + "JOIN ORDERITEMS I ON I.ORDER_ID = O.ID JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID "
                + "GROUP BY C.ID, C.NAME ORDER BY C.NAME");
        
        JpaResultMapper mapper = new JpaResultMapper();
        
        List<CustomerInfoDTO> list = mapper.list(q, CustomerInfoDTO.class);

        Assert.assertNotNull(list);
    }

    @Test @Ignore
    public void calculateRevenueConstructorExpressionJoinOn() {
        Query q = em.createQuery(
                "SELECT "
                + "NEW entity.CustomerInfoDTO(c.id, c.name, SUM(i.product.price)) "
                + "FROM Customer c "
                + "JOIN c.orders o ON o.customer_id = c.id "
                + "JOIN o.items i "
                + "GROUP BY c.name");

        List<CustomerInfoDTO> list = q.getResultList();

        Assert.assertNotNull(list);
    }

}

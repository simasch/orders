package control;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
        List<CustomerInfoDTO> list = customerService.calculateRevenueConstructorExpression();

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void calculateRevenueQlrm() {
        List<CustomerInfoDTO> list = customerService.calculateRevenueQlrm();

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void calculateRevenueJooq() {
        List<CustomerInfoDTO> list = customerService.calculateRevenueJooq();

        Assert.assertTrue(list.size() > 0);
    }

}

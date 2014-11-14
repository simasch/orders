package test;

import control.RevenueCalculator;
import entity.CustomerRevenue;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author martinellisi
 */
public class OrderTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static RevenueCalculator rc;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("orders");
        em = emf.createEntityManager();

        rc = new RevenueCalculator(em);
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
    public void calculateRevenueNaive() {
        List<CustomerRevenue> list = rc.calculateRevenueNaive();

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void calculateRevenueConstructorExpression() {
        List<CustomerRevenue> list = rc.calculateRevenueConstructorExpression();

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void calculateRevenueQlrm() {
        List<CustomerRevenue> list = rc.calculateRevenueQlrm();

        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void calculateRevenueJooq() {
        List<CustomerRevenue> list = rc.calculateRevenueJooq();

        Assert.assertTrue(list.size() > 0);
    }

}

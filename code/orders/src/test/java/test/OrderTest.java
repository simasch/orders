package test;

import control.RevenueCalculator;
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
        double revenue = rc.calculateRevenueNaive();

        Assert.assertTrue(revenue > 0);

        printRevenue(revenue);
    }

    @Test
    public void calculateRevenueConstructorExpression() {
        double revenue = rc.calculateRevenueConstructorExpression();

        Assert.assertTrue(revenue > 0);

        printRevenue(revenue);
    }

    @Test
    public void calculateRevenueQlrm() {
        double revenue = rc.calculateRevenueQlrm();

        Assert.assertTrue(revenue > 0);

        printRevenue(revenue);
    }

    @Test
    public void calculateRevenueJooq() {
        double revenue = rc.calculateRevenueJooq();

        Assert.assertTrue(revenue > 0);

        printRevenue(revenue);
    }

    private void printRevenue(double revenue) {
        System.out.println();
        System.out.println("--> Revenue: " + revenue);
        System.out.println();
    }

}

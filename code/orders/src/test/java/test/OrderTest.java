package test;

import control.RevenueCalculator;
import entity.Customer;
import entity.Order;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author martinellisi
 */
public class OrderTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("orders");
        em = emf.createEntityManager();
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
        RevenueCalculator rc = new RevenueCalculator(em);
        double revenue = rc.calculateRevenueNaive();
        
        
        Assert.assertTrue(revenue > 0);
        
        System.out.println();
        System.out.println("--> Revenue: " + revenue);
        System.out.println();
        
    }
    @Test
    public void calculateRevenueConsturctorExpression() {
        RevenueCalculator rc = new RevenueCalculator(em);
        double revenue = rc.calculateRevenueConstructorExpression();
        
        
        Assert.assertTrue(revenue > 0);
        
        System.out.println();
        System.out.println("--> Revenue: " + revenue);
        System.out.println();
        
    }
}

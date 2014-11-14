package control;

import entity.Customer;
import entity.Order;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.qlrm.mapper.JpaResultMapper;
import static org.jooq.impl.DSL.*;
import static orders.orders.Tables.*;
import org.jooq.Record1;
import org.jooq.Result;

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

    public double calculateRevenueQlrm() {
        Query q = em.createNativeQuery("SELECT SUM(P.PRICE) FROM CUSTOMERS C JOIN ORDERS O ON O.CUSTOMER_ID = C.ID JOIN ORDERITEMS I ON I.ORDER_ID = O.ID JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID");
        JpaResultMapper mapper = new JpaResultMapper();
        Double total = mapper.uniqueResult(q, Double.class);
        return total;
    }

    public double calculateRevenueJooq() {
        em.getTransaction().begin();
        Connection conn = em.unwrap(java.sql.Connection.class);

        DSLContext create = DSL.using(conn, SQLDialect.DERBY);

        BigDecimal result = create.select(sum(PRODUCTS.PRICE)).
                from(CUSTOMERS).
                join(ORDERS).on(ORDERS.CUSTOMER_ID.eq(CUSTOMERS.ID)).
                join(ORDERITEMS).on(ORDERITEMS.ORDER_ID.eq(ORDERS.ID)).
                join(PRODUCTS).on(PRODUCTS.ID.eq(ORDERITEMS.PRODUCT_ID)).
                fetchOneInto(BigDecimal.class);

        em.getTransaction().commit();

        return result.doubleValue();
    }

}

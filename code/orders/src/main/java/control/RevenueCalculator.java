package control;

import entity.Customer;
import entity.CustomerRevenue;
import entity.Order;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
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

    public List<CustomerRevenue> calculateRevenueNaive() {
        TypedQuery q = em.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = q.getResultList();

        List<CustomerRevenue> list = new ArrayList<>();

        for (Customer c : customers) {
            double revenue = 0d;
            for (Order o : c.getOrders()) {
                revenue += o.getPrice();
            }
            list.add(new CustomerRevenue(c.getName(), revenue));
        }

        return list;
    }

    public List<CustomerRevenue> calculateRevenueConstructorExpression() {
        Query q = em.createQuery("SELECT NEW entity.CustomerRevenue(c.name, SUM(i.product.price)) FROM Customer c JOIN c.orders o JOIN o.items i GROUP BY c.name");
        return q.getResultList();
    }

    public List<CustomerRevenue> calculateRevenueQlrm() {
        Query q = em.createNativeQuery(
                "SELECT C.NAME, SUM(P.PRICE) FROM CUSTOMERS C JOIN ORDERS O ON O.CUSTOMER_ID = C.ID JOIN ORDERITEMS I ON I.ORDER_ID = O.ID JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID GROUP BY C.NAME");
        JpaResultMapper mapper = new JpaResultMapper();
        return mapper.list(q, CustomerRevenue.class);
    }

    public List<CustomerRevenue> calculateRevenueJooq() {
        em.getTransaction().begin();
        Connection conn = em.unwrap(java.sql.Connection.class);

        DSLContext create = DSL.using(conn, SQLDialect.DERBY);

        List<CustomerRevenue> list = create.select(CUSTOMERS.NAME, sum(PRODUCTS.PRICE)).
                from(CUSTOMERS).
                join(ORDERS).on(ORDERS.CUSTOMER_ID.eq(CUSTOMERS.ID)).
                join(ORDERITEMS).on(ORDERITEMS.ORDER_ID.eq(ORDERS.ID)).
                join(PRODUCTS).on(PRODUCTS.ID.eq(ORDERITEMS.PRODUCT_ID)).
                groupBy(CUSTOMERS.NAME).
                fetchInto(CustomerRevenue.class);

        em.getTransaction().commit();
        
        return list;
    }

}

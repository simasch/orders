package control;

import entity.Customer;
import entity.Order;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.qlrm.mapper.JpaResultMapper;
import static org.jooq.impl.DSL.*;
import static orders.orders.Tables.*;

@Stateless
public class CustomerService {

    @PersistenceContext
    protected EntityManager em;

    public List<Customer> getCustomers() {
        TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c", Customer.class);
        return q.getResultList();
    }

    public List<CustomerInfoDTO> calculateRevenueConstructorExpression() {
        Query q = em.createQuery("SELECT NEW control.CustomerInfoDTO(c.name, SUM(i.product.price)) FROM Customer c JOIN c.orders o JOIN o.items i GROUP BY c.name");
        return q.getResultList();
    }

    public List<CustomerInfoDTO> calculateRevenueQlrm() {
        Query q = em.createNativeQuery(
                "SELECT C.NAME, SUM(P.PRICE) FROM CUSTOMERS C JOIN ORDERS O ON O.CUSTOMER_ID = C.ID JOIN ORDERITEMS I ON I.ORDER_ID = O.ID JOIN PRODUCTS P ON P.ID = I.PRODUCT_ID GROUP BY C.NAME");
        JpaResultMapper mapper = new JpaResultMapper();
        return mapper.list(q, CustomerInfoDTO.class);
    }

    public List<CustomerInfoDTO> calculateRevenueJooq() {
        em.getTransaction().begin();
        Connection conn = em.unwrap(java.sql.Connection.class);

        DSLContext create = DSL.using(conn, SQLDialect.DERBY);

        List<CustomerInfoDTO> list = create.select(CUSTOMERS.NAME, sum(PRODUCTS.PRICE)).
                from(CUSTOMERS).
                join(ORDERS).on(ORDERS.CUSTOMER_ID.eq(CUSTOMERS.ID)).
                join(ORDERITEMS).on(ORDERITEMS.ORDER_ID.eq(ORDERS.ID)).
                join(PRODUCTS).on(PRODUCTS.ID.eq(ORDERITEMS.PRODUCT_ID)).
                groupBy(CUSTOMERS.NAME).
                fetchInto(CustomerInfoDTO.class);

        em.getTransaction().commit();

        return list;
    }

}
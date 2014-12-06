package jooq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static orders.orders.Tables.CUSTOMERS;
import static orders.orders.Tables.ORDERITEMS;
import static orders.orders.Tables.ORDERS;
import static orders.orders.Tables.PRODUCTS;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.sum;
import static org.junit.Assert.*;
import org.junit.Test;

public class JooqDemoTest {
    
    @Test
    public void getCustomersWithJooq() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/orders", "orders", "orders")) {
          
            DSLContext create = DSL.using(conn, SQLDialect.DERBY);
            
            List<CustomerInfoDTO> list = create.
                    select(CUSTOMERS.ID, CUSTOMERS.LASTNAME, CUSTOMERS.FIRSTNAME, sum(PRODUCTS.PRICE)).
                    from(CUSTOMERS).
                    join(ORDERS).on(ORDERS.CUSTOMER_ID.eq(CUSTOMERS.ID)).
                    join(ORDERITEMS).on(ORDERITEMS.ORDER_ID.eq(ORDERS.ID)).
                    join(PRODUCTS).on(PRODUCTS.ID.eq(ORDERITEMS.PRODUCT_ID)).
                    groupBy(CUSTOMERS.ID, CUSTOMERS.LASTNAME, CUSTOMERS.FIRSTNAME).
                    orderBy(CUSTOMERS.LASTNAME, CUSTOMERS.FIRSTNAME).
                    fetchInto(CustomerInfoDTO.class);
            
            
            assertNotNull(list);
            assertFalse(list.isEmpty());
        }
    }
}

package generator;

import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.Product;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestDataGenerator {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        buildEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Product product = new Product();
        product.setName("Aschenbecher");
        product.setPrice(2.5d);
        em.persist(product);
        transaction.commit();

        for (int i = 0; i < 300; i++) {
            transaction = em.getTransaction();
            transaction.begin();

            Customer customer = new Customer();
            customer.setName("Peter Müller");
            em.persist(customer);

            for (int j = 0; j < 500; j++) {
                Order order = new Order();
                order.setCustomer(customer);
                customer.getOrders().add(order);

                for (int k = 0; k < 100; k++) {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(1);

                    order.getItems().add(item);
                }
            }
            transaction.commit();
        }

        closeEntityManager();
    }

    private static void buildEntityManager() {
        emf = Persistence.createEntityManagerFactory("orders");
        em = emf.createEntityManager();
    }

    private static void closeEntityManager() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

}

package generator;

import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.Product;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.soqqo.datagen.RandomDataGenerator;
import org.soqqo.datagen.config.DataTypes.Name;
import org.soqqo.datagen.config.GenConfig;

public class TestDataGenerator {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        buildEntityManager();

        Random random = new Random();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Product product = new Product();
        product.setName("Bleistift");
        product.setPrice(2.5d);
        em.persist(product);
        transaction.commit();

        RandomDataGenerator rdg = new RandomDataGenerator();
        List<Customer> customers = rdg.generateList(400,
                new GenConfig()
                .name(Name.Firstname, "firstname")
                .name(Name.Lastname, "lastname"), Customer.class);

        for (Customer customer : customers) {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(customer);

            for (int j = 0; j < random.nextInt(50 - 1 + 1) + 1; j++) {
                Order order = new Order();
                order.setCustomer(customer);
                customer.getOrders().add(order);

                for (int k = 0; k < random.nextInt(20 - 1 + 1) + 1; k++) {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(random.nextInt(10 - 1 + 1) + 1);

                    order.getItems().add(item);
                }
            }
            transaction.commit();
        }

        closeEntityManager();
    }

    private static void buildEntityManager() {
        emf = Persistence.createEntityManagerFactory("orders-test");
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

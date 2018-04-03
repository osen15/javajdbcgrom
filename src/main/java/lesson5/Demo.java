package lesson5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Demo {
    //  public static void main(String[] args) {
    //   ProductRepository productRepository = new ProductRepository();
    //   Product product = new Product();
    //   product.setId(12);
    //   product.setName("robocop");
    //   product.setDescription("for children");
    //   product.setPrice(1000);

    //productRepository.save(product);

    // productRepository.delete(99);

    // productRepository.update(product);
//}

    private Session session = createSessionFactory().openSession();

    public void save(Product product) {
        session.getTransaction().begin();
        session.save(product);
        session.getTransaction().commit();

        session.close();


    }

    private SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

}
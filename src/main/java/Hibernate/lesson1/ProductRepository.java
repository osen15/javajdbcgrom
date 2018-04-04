package Hibernate.lesson1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ProductRepository {

    private Session session = createSessionFactory().openSession();


    public void save(Product product) {
        session.getTransaction().begin();
        session.save(product);
        session.getTransaction().commit();

        session.close();


    }

    public void delete(long id) {
        session.getTransaction().begin();
        session.delete(session.get(Product.class, id));
        session.getTransaction().commit();

        session.close();

    }

    public void update(Product product) {
        session.getTransaction().begin();
        session.update(product);
        session.getTransaction().commit();

        session.close();

    }

    public SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }


}

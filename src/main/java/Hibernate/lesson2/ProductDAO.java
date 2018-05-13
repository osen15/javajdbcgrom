package Hibernate.lesson2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAO {


    private SessionFactory sessionFactory;


    public Product save(Product product) {
        //1 create session/tr
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //2 action
            session.save(product);

            //3 close session/tr
            tr.commit();


        } catch (HibernateException e) {
            System.err.println("save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("save is done!");
        return product;
    }

    public Product update(Product product) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();


            session.update(session.load(Product.class, product.getId()));

            tr.commit();

        } catch (HibernateException e) {
            System.err.println("update is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("update is done!");
        return product;
    }


    public void delete(Product product) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            session.delete(session.get(Product.class, product.getId()));

            tr.commit();

        } catch (HibernateException e) {
            System.err.println("delete is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("delete is done!");


    }

    public void saveAll(List<Product> products) {

        //1 create session/tr
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //2 action
            for (Product product : products) {
                session.save(product);
            }
            //3 close session/tr
            tr.commit();


        } catch (HibernateException e) {
            System.err.println("save all is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("save all is done!");

    }

    public void updateAll(List<Product> products) {

        //1 create session/tr
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //2 action
            for (Product product : products) {
                session.update(session.load(Product.class, product.getId()));
            }
            //3 close session/tr
            tr.commit();


        } catch (HibernateException e) {
            System.err.println("delete all is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("delete all is done!");

    }

    public void deleteAll(List<Product> products) {

        //1 create session/tr
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //2 action
            for (Product product : products) {
                session.delete(session.load(Product.class, product.getId()));
            }
            //3 close session/tr
            tr.commit();


        } catch (HibernateException e) {
            System.err.println("update all is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
        System.out.println("update all is done!");

    }


    private SessionFactory createSessionFactory() {
        //Singleton pattern
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

}

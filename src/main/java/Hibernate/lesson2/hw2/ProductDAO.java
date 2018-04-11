package Hibernate.lesson2.hw2;


import Hibernate.lesson2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;


import java.util.List;


public class ProductDAO {
    private SessionFactory sessionFactory;

    public Product findById(Long id) {
        Product product = new Product();

        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT WHERE ID = ?");
            nativeQuery.setParameter(1, id);
            product = (Product) nativeQuery.uniqueResult();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return product;
    }

    public List findByName(String name) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT p WHERE p.NAME = ?");
            nativeQuery.setParameter(1, name);
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }

    public List containedName(String name) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT p WHERE p.NAME like ?");
            nativeQuery.setParameter(1, "%" + name + "%");
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }

    public List findByPrice(Integer price, Integer delta) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT WHERE PRICE >= ? AND PRICE <= ?");
            nativeQuery.setParameter(1, price - delta);
            nativeQuery.setParameter(2, price + delta);
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }

    public List findByNameSortedAsc(String name) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT p WHERE" +
                    " p.NAME = PR_NAME ORDER BY p.NAME ASC");
            nativeQuery.setParameter("PR_NAME", name);
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }

    public List findByNameSortedDesc(String name) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT p WHERE" +
                    " p.NAME = PR_NAME ORDER BY p.NAME DESC");
            nativeQuery.setParameter("PR_NAME", name);
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }


    public List findByPriceSortedDesc(Integer price, Integer delta) {
        List products = null;


        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM PRODUCT  WHERE" +
                    " PRICE >= ? AND PRICE <= ? ORDER BY PRICE DESC ");
            nativeQuery.setParameter(1, price - delta);
            nativeQuery.setParameter(2, price + delta);
            products = nativeQuery.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("failed");
            System.err.println(e.getMessage());

        }
        return products;
    }


    private SessionFactory createSessionFactory() {
        //Singleton pattern
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

}

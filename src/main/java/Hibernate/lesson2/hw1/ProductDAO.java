package Hibernate.lesson2.hw1;


import Hibernate.lesson2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class ProductDAO {
    private SessionFactory sessionFactory;

    public Product findById(Long id) {
        Product product = new Product();

        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from Product where id = :id");
            query.setParameter("id", id);
            product = (Product) query.uniqueResult();

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

            Query query = session.createQuery("from Product where name = :name");
            query.setParameter("name", name);
            products = query.getResultList();

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

            Query query = session.createQuery("from Product where name like ?");
            query.setParameter(0, "%" + name + "%");
            products = query.getResultList();

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

            Query query = session.createQuery("from Product  where price >= ? and price <= ?");
            query.setParameter(0, price - delta);
            query.setParameter(0, price + delta);
            products = query.getResultList();

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

            Query query = session.createQuery("from Product where name = :name order by name asc");
            query.setParameter("name", name);
            products = query.getResultList();

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

            Query query = session.createQuery("from Product where name = :name order by name desc ");
            query.setParameter("name", name);
            products = query.getResultList();

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

            Query query = session.createQuery("from Product  where price >= ? and price <= ? order by price desc");
            query.setParameter(0, price - delta);
            query.setParameter(0, price + delta);

            products = query.getResultList();
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
